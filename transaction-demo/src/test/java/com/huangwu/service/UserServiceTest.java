package com.huangwu.service;

import com.huangwu.TransactionDemoApplication;
import com.huangwu.domain.GrootUser;
import com.huangwu.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.huangwu
 * @Author: huangwu
 * @Date: 2018/7/29 16:59
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionDemoApplication.class)
public class UserServiceTest {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Resource
    private EtcdModifyService etcdModifyService;

    /**
     * spring @Transactional在测试代码中使用时rollback属性默认是true的，
     * 也就是不管操作是否有异常都会执行回滚操作，所以测试时需要指定rollback属性为false
     *
     * @throws Exception
     */
    @Test
    public void testAddUser() {
        userMapper.addUser(createUser("张三"));
    }

    /**
     * 因为batchAddUser方法上已经加了事物，所以当第九条数据因为名称超过定义的最大长度时就会自动回滚
     */
    @Test
    public void batchAddUser() {
        List<GrootUser> users = createUsers(10);
        userService.batchAddUser(users);
    }

    @Test
    public void testAddUserAndEtcdModify() {
        userService.addUserAndEtcdModify(createUser("张大胖"),etcdModifyService.createEtcdModifyBean(111111));
    }


    private GrootUser createUser(String username) {
        GrootUser user = new GrootUser();
        user.setUserName(username);
        user.setUserPhone("13588781245");
        user.setPassword(username + "123");
        user.setSalt("123");
        user.setEmail("23131@163.com");
        user.setRoleId(1L);
        user.setIsDeleted(0);
        user.setLoginTimes(1L);
        return user;
    }

    private List<GrootUser> createUsers(int num) {
        List<GrootUser> users = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            if (i == num -1) {
                GrootUser user = createUser("名字很长长长长长长长长长长长长长长");
                users.add(user);
            } else {
                GrootUser user = createUser("猪悟能" + i);
                users.add(user);
            }
        }
        return users;
    }

}
