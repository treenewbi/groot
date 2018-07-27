package com.huangwu.etcd.aop.demo4;

import com.huangwu.etcd.aop.Hello;
import com.huangwu.etcd.aop.Hello;

/**
 * @Package: com.huangwu.etcd.aop.demo3
 * @Author: huangwu
 * @Date: 2018/7/5 20:28
 * @Description:
 * @LastModify:
 */
public class HelloImpl implements Hello {
    @Override
    public void smile() {
        System.out.println("Im smile...");
    }

    @Override
    public void cry() {
        System.out.println("Im crying...");
    }
}
