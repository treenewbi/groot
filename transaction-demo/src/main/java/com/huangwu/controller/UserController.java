package com.huangwu.controller;

import com.huangwu.domain.GrootUser;
import com.huangwu.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Package: com.huangwu.controller
 * @Author: huangwu
 * @Date: 2018/7/29 15:51
 * @Description:
 * @LastModify:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    private GrootUser createUser(String username) {
        GrootUser user = new GrootUser();
        user.setUserName(username);
        user.setUserPhone("13588781245");
        user.setPassword(username + "123");
        user.setSalt("123");
        user.setEmail("23131@163.com");
        user.setRoleId(1L);
        user.setIsDeleted(0);
        return user;
    }

    @PostMapping("add")
    public int addUser() throws Exception {
        int val = userService.addUser(createUser("大鸟"));
        return val;
    }


}
