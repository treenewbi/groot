package com.huangwu.etcd.aop.demo3;

import com.huangwu.etcd.aop.Hello;

/**
 * @Package: com.huangwu.etcd.aop.demo3
 * @Author: huangwu
 * @Date: 2018/7/5 20:29
 * @Description:
 * @LastModify:
 */
public class HelloClient {
    public static void main(String[] args) {
        Hello hello = new JDKDynamicProxy(new HelloImpl()).getInstanse();
        hello.cry();
        hello.smile();
    }
}
