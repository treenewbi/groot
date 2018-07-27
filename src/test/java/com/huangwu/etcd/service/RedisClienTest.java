package com.huangwu.etcd.service;

import com.huangwu.MainApplication;
import com.huangwu.util.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Package: com.huangwu.etcd.service
 * @Author: huangwu
 * @Date: 2018/6/3 20:53
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class RedisClienTest {
    private CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void test1() {
        RedisKey redisKey = new RedisKey("123");
        RedisKey redisKey2 = new RedisKey("user");
        List<RedisKey> keys = new ArrayList<>();
        keys.add(redisKey);
        keys.add(redisKey2);
        RedisClient client = new RedisClient(keys);
        RedisClient.RedisKeyChangeListener listener = new RedisClient.RedisKeyChangeListener() {
            @Override
            public void onRedisKeyChange(String key, Object value) {
                System.out.println("change key: " + key + ", value: " + value);
            }
        };
        client.addListeners(listener);
        RedisClient.WatchThread watchThread = client.getWatchThread();
        try {
            Thread.sleep(15000);
            watchThread.interrupt();
            System.out.println("start ...");
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
