package com.huangwu.etcd.other.genericity;

import com.huangwu.common.ErrorCode;
import com.huangwu.exception.GlobalException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Package: com.huangwu.etcd.other.genericity
 * @Author: huangwu
 * @Date: 2018/7/16 9:24
 * @Description:
 * @LastModify:
 */
public class GenericTest<T> {

    private T value;

    public GenericTest(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    /**
     * <E>用于声明泛型类型，此处如果用T则不需要<T>再次声明，因为类已经声明了T
     *
     * @param msg
     * @param <E>
     * @return
     */
    public <E> E print(E msg) {
        return msg;
    }

    public Object readFile(String fileName) throws GlobalException {
        File file = new File(fileName);
        throw new GlobalException(ErrorCode.USER_PHONE_EXIST);
    }

    public void queryUsersByLoginTime(String name) {
        assert name != null : "begin date should not be null";
        assert name == null : "name should not be null";
        System.out.println("name:" + name);
    }

    public static void main(String[] args) {
        try {
            GenericTest test = new GenericTest(1);
            test.queryUsersByLoginTime(null);
        } catch (Throwable throwable) {
            System.out.println(111);
        }
    }
}
