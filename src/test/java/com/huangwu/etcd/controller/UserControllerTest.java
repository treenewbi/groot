package com.huangwu.etcd.controller;

import com.huangwu.MainApplication;
import com.huangwu.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Package: com.huangwu.etcd.controller
 * @Author: huangwu
 * @Date: 2018/6/20 9:55
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class UserControllerTest {

    @Resource
    private Object userController;
}
