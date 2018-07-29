package com.huangwu.etcd.aop.demo3;

import com.huangwu.etcd.aop.Greeting;

/**
 * @Package: com.huangwu.etcd.aop.demo3
 * @Author: huangwu
 * @Date: 2018/7/5 20:18
 * @Description:
 * @LastModify:
 */
public class GreetingImpl implements Greeting {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }
}
