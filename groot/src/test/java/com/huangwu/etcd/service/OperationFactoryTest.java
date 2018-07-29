package com.huangwu.etcd.service;

import com.huangwu.MainApplication;
import com.huangwu.common.OperationFactory;
import com.huangwu.service.IUserAddOperation;
import com.huangwu.service.impl.UserAddOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Package: com.huangwu.etcd.service
 * @Author: huangwu
 * @Date: 2018/7/19 21:30
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class OperationFactoryTest {

    @Resource
    private OperationFactory operationFactory;

    @Test
    public void testGetOperation() throws Exception {
//        IUserAddOperation bean = operationFactory.getOperation(IUserAddOperation.class);
//        System.out.println(bean);
        System.out.println(IUserAddOperation.class);
        System.out.println(UserAddOperation.class);
    }
}
