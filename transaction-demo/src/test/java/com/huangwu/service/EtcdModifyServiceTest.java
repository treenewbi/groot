package com.huangwu.service;

import com.huangwu.TransactionDemoApplication;
import com.huangwu.domain.EtcdModify;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/7/31 21:52
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionDemoApplication.class)
public class EtcdModifyServiceTest {

    @Resource
    private EtcdModifyService etcdModifyService;

    @Test
    public void testAddEtcdModify() {
        etcdModifyService.addEtcdModify(etcdModifyService.createEtcdModifyBean(1234567));
    }


}
