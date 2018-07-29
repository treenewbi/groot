package com.huangwu.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 定义一个web层请求的日志切面
 *
 * @Package: com.huangwu.config
 * @Author: huangwu
 * @Date: 2018/5/20 12:53
 * @Description:
 * @LastModify:
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadLocal<Long> beginTime = new ThreadLocal<>();

    /**
     * 构造一个切点
     * execution表达式解释：
     * 1、第一个*号：表示返回类型，*号表示所有的类型。
     * 2、huangwu.controller代表包名，后面的两个点表示当前包和当前的所有子包
     * 3、第二个*号：表示类名，*号表示所有的类
     * *(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数
     */
    @Pointcut("execution(public * com.huangwu.controller..*.*(..))")
    public void webRequestLog() {
    }

    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        beginTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        logger.info("Received a request from ip[{}],url[{}]", ip, url);
    }

    @AfterReturning("webRequestLog()")
    public void doAfter(JoinPoint joinPoint) throws Exception {
        Long requestTime = System.currentTimeMillis() - beginTime.get();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        logger.info("Processing successfully requests from ip[{}],url[{}] in {} ms", ip, url, requestTime);
    }

}
