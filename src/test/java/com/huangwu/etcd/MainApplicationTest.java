package com.huangwu.etcd;

import com.huangwu.redis.message.receiver.LogReceiver;
import com.huangwu.redis.message.receiver.LoginReceiver;
import com.huangwu.service.IMessageListenerContainerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @Package: com.huangwu.etcd
 * @Author: huangwu
 * @Date: 2018/6/2 13:34
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MainApplicationTest {

    CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private RedisMessageListenerContainer container;

    @Autowired
    private IMessageListenerContainerService messageListenerContainerService;

    @Autowired
    private LogReceiver logReceiver;


    @Test
    public void testMessage() throws InterruptedException {
//        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(new LoginReceiver(), "handleMessage");
        messageListenerContainerService.addMessageListener(new LoginReceiver(), "modifyLog1", "handleMessage");
        Thread.sleep(15000);
        System.out.println("sleep end");
        messageListenerContainerService.removeMessageListener(logReceiver);
        sleepForerver();
    }

    private void sleepForerver() throws InterruptedException {
        latch.await();
    }
}
