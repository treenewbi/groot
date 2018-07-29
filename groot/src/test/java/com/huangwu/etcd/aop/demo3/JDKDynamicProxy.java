package com.huangwu.etcd.aop.demo3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Package: com.huangwu.etcd.aop.demo3
 * @Author: huangwu
 * @Date: 2018/7/5 20:30
 * @Description:
 * @LastModify:
 */
public class JDKDynamicProxy implements InvocationHandler {
    private Object target;

    public JDKDynamicProxy(Object target) {
        this.target = target;
    }

    public <T> T getInstanse() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    public void before() {
        System.out.println("Before .......");
    }

    public void after() {
        System.out.println("After ........");
    }
}
