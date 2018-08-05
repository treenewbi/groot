package com.huangwu.etcd.service;

import com.huangwu.MainApplication;
import com.huangwu.domain.GrootUser;
import com.huangwu.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.huangwu.etcd.service
 * @Author: huangwu
 * @Date: 2018/7/1 11:09
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class UserServiceTest {

    @Resource
    private IUserService userService;

    @Test
    @Transactional
    public void addUserTest() throws Exception {
        for (int i = 0; i < 10; i++) {
            GrootUser user = new GrootUser();
            user.setUserName("test" + i);
            user.setUserPhone("13588714600");
            user.setPassword("123456");
            user.setSalt("123");
            user.setEmail("3143@qq.com");
            if (i == 7) {
                user.setUserName("test777777777777777777777");
            }
            userService.addUser(user);
        }
    }

    @Test
    @Transactional
    public void testBatchAddUser() throws Exception {
        List<GrootUser> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            GrootUser user = new GrootUser();
            user.setUserName("詹三" + i);
            user.setPassword("123456");
            user.setSalt("123");
            user.setUserPhone("13588711111");
            user.setRoleId(1L);
            user.setIsDeleted(0);
            users.add(user);
        }
        int num = userService.batchAddUser(users);
        System.out.println(num);
    }
}
