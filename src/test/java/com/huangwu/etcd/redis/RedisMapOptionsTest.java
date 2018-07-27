package com.huangwu.etcd.redis;

import com.huangwu.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Package: com.huangwu.etcd.redis
 * @Author: huangwu
 * @Date: 2018/6/5 20:48
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class RedisMapOptionsTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void putTest() {
        redisTemplate.opsForHash().put("hash", "k1", "v1");
        System.out.println();
    }
}
