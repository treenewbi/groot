package com.huangwu.etcd.service;

import com.huangwu.common.OperationFactory;
import com.huangwu.service.impl.UserAddOperation;
import com.huangwu.MainApplication;
import com.huangwu.common.OperationFactory;
import com.huangwu.domain.GrootUser;
import com.huangwu.service.IUserOperationService;
import com.huangwu.service.impl.UserAddOperation;
import com.huangwu.service.impl.UserOperationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package: com.huangwu.etcd.service
 * @Author: huangwu
 * @Date: 2018/7/16 12:00
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class UserOperationServiceTest {

    @Resource
    private OperationFactory operationFactory;
    @Resource
    private IUserOperationService userOperationService;

    @Test
    public void queryUserByIdTest() throws Exception {
        GrootUser user = userOperationService.queryUserById(8);
        System.out.println(user);
    }

    @Test
    public void queryUsersByRoleIdTest() throws Exception {
        List<GrootUser> users = userOperationService.queryUsersByRoleId(1);
    }

    @Test
    public void queryUsersByLoginTimeTest() throws Exception {
//        List<GrootUser> users = userOperationService.queryUsersByLoginTime()
    }

    @Test
    public void updateUserTest() throws Exception {
        GrootUser user = new GrootUser();
        user.setUserName("hello");
        user.setEmail("312313@qq.com");
        user.setSalt("dasda");
        user.setPassword("sdsafasd");
        user.setUserPhone("13590876098");
        user.setUserId(1L);
        int num = userOperationService.updateUser(user);
    }

    @Test
    public void operationTest() throws Exception {
        UserAddOperation operation = operationFactory.getOperation(UserAddOperation.class);
    }


}
