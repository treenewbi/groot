package com.huangwu.etcd.aop.demo4;

import com.huangwu.etcd.aop.Hello;
import com.huangwu.etcd.aop.Hello;

/**
 * @Package: com.huangwu.etcd.aop.demo4
 * @Author: huangwu
 * @Date: 2018/7/5 20:54
 * @Description:
 * @LastModify:
 */
public class Client {
    public static void main(String[] args) {
        Hello hello = CglbDynamicProxy.getInstanse().getProxy(HelloImpl.class);
        hello.smile();

    }
}
