package com.huangwu.etcd.rabbitmq;

import com.huangwu.rabbitmq.sender.HelloSender;
import com.huangwu.rabbitmq.sender.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Package: com.huangwu.etcd.rabbitmq
 * @Author: huangwu
 * @Date: 2018/6/8 8:40
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqHelloTest {

    @Autowired
    private HelloSender helloSender;

    @Test
    public void hello() throws Exception {
        helloSender.send();
    }

}
