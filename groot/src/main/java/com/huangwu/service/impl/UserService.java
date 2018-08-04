package com.huangwu.service.impl;

import com.huangwu.common.ErrorCode;
import com.huangwu.common.constant.SeckillConstant;
import com.huangwu.domain.GrootUser;
import com.huangwu.domain.vo.LoginVo;
import com.huangwu.exception.GlobalException;
import com.huangwu.mapper.UserMapper;
import com.huangwu.redis.SeckillKey;
import com.huangwu.redis.UserKey;
import com.huangwu.service.IUserService;
import com.huangwu.util.MD5Util;
import com.huangwu.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.huangwu.common.ErrorCode.USER_ACCOUNT_ERROR;
import static com.huangwu.common.ErrorCode.USER_PASSWORD_ERROR;

/**
 * @Package: com.huangwu.service.impl
 * @Author: huangwu
 * @Date: 2018/5/27 11:29
 * @Description:
 * @LastModify:
 */
@Service
public class UserService implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean register(GrootUser user) throws Exception {
        GrootUser dbUser = userMapper.findUserByName(user.getUserName());
        if (dbUser != null) {
            throw new GlobalException(ErrorCode.USER_NAME_EXIST);
        }
        dbUser = userMapper.findUserByPhone(user.getUserPhone());
        if (dbUser != null) {
            throw new GlobalException(ErrorCode.USER_PHONE_EXIST);
        }
        // 生成一个随机salt
        user.setSalt(StringHelper.getUUID());
        String dbPass = MD5Util.formPassToDBPass(user.getPassword(), user.getSalt());
        user.setPassword(dbPass);
        return userMapper.insertUser(user) == 1;
    }

    @Override
    public GrootUser login(HttpServletResponse response, LoginVo loginVo) throws Exception {
        String account = loginVo.getAccount();
        String formPass = loginVo.getPassword();
        //判断账号是否存在，允许用户名或者手机号登录
        GrootUser user = userMapper.getUserByNameOrPhone(account);
        if (user == null) {
            throw new GlobalException(USER_ACCOUNT_ERROR);
        }
        //验证密码
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, salt);
        if (!dbPass.equals(calcPass)) {
            throw new GlobalException(USER_PASSWORD_ERROR);
        }
        //生成cookie

        return null;
    }

    @Override
    public GrootUser getByToken(HttpServletResponse response, String token) throws Exception {
        ValueOperations<String, GrootUser> option = redisTemplate.opsForValue();
        GrootUser user = option.get(UserKey.token.realKey(token));
        //延长有限期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    @Override
    public GrootUser queryUser(String username) throws Exception {
        return userMapper.queryUser(username);
    }

    @Override
    public void addUser(GrootUser user) throws Exception {
        userMapper.addUser(user);
    }

    private void addCookie(HttpServletResponse response, String token, GrootUser user) {
        redisTemplate.opsForValue().set(UserKey.token.realKey(token), user, UserKey.token.expireSeconds(), TimeUnit.SECONDS);
        Cookie cookie = new Cookie(SeckillConstant.COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
