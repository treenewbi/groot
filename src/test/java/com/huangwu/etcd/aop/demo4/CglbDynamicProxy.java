package com.huangwu.etcd.aop.demo4;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Package: com.huangwu.etcd.aop.demo4
 * @Author: huangwu
 * @Date: 2018/7/5 20:46
 * @Description:
 * @LastModify:
 */
public class CglbDynamicProxy implements MethodInterceptor {

    public static CglbDynamicProxy instanse = new CglbDynamicProxy();

    private CglbDynamicProxy() {

    }

    public static CglbDynamicProxy getInstanse() {
        return instanse;
    }

    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }

    public void before() {
        System.out.println("Before....");
    }

    public void after() {
        System.out.println("After...");
    }
}
