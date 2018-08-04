package com.huangwu.acceess;

import com.alibaba.fastjson.JSON;
import com.huangwu.common.ErrorCode;
import com.huangwu.common.Result;
import com.huangwu.common.constant.SeckillConstant;
import com.huangwu.domain.GrootUser;
import com.huangwu.redis.AccessKey;
import com.huangwu.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * 认证拦截器
 *
 * @Package: com.huangwu.acceess
 * @Author: huangwu
 * @Date: 2018/6/3 12:25
 * @Description:
 * @LastModify:
 */
public class AcceessInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            GrootUser user = getUser(request, response);
            request.setAttribute(SeckillConstant.SESSION_INFO, user);
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if (needLogin) {
                if (user == null) {
                    render(response, ErrorCode.SESSION_ERROR);
                    return false;
                }
                key = key + user.getUserId();
            }
            AccessKey accessKey = AccessKey.withExpire(seconds);
            //查询缓存中的访问次数
            Integer count = (Integer) redisTemplate.opsForValue().get(accessKey.realKey(key));
            if (count == null) {
                redisTemplate.opsForValue().set(accessKey.realKey(key), 1, accessKey.expireSeconds(), TimeUnit.SECONDS);
            } else if (count <= maxCount) {
                redisTemplate.opsForValue().increment(accessKey.realKey(key), 1L);
            } else {
                render(response, ErrorCode.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    private GrootUser getUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String paramToken = request.getParameter(SeckillConstant.COOKI_NAME_TOKEN);
        String cookieToken = getCookieValue(request, SeckillConstant.COOKI_NAME_TOKEN);
        if (StringUtils.isBlank(paramToken) && StringUtils.isBlank(cookieToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void render(HttpServletResponse response, ErrorCode errorCode) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(errorCode));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
