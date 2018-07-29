package com.huangwu.etcd.aop.demo3;

import com.huangwu.etcd.aop.Greeting;

/**
 * @Package: com.huangwu.etcd.aop.demo3
 * @Author: huangwu
 * @Date: 2018/7/5 20:17
 * @Description:
 * @LastModify:
 */
public class Client {

    public static void main(String[] args) {
        Greeting greeting = new JDKDynamicProxy(new GreetingImpl()).getInstanse();
        greeting.sayHello("张三");
    }
}
