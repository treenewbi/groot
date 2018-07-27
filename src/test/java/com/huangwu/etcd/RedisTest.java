package com.huangwu.etcd;

import com.huangwu.MainApplication;
import com.huangwu.domain.GrootUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @Package: com.huangwu.etcd
 * @Author: huangwu
 * @Date: 2018/6/2 10:46
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class RedisTest {
    @Autowired
        private RedisTemplate redisTemplate;
    @Test
    public void testSet() {
//        redisTemplate.opsForValue().set("111","356542");
        GrootUser user = new GrootUser();
        user.setUserPhone("13588712333");
        user.setUserName("");
        redisTemplate.opsForValue().set("user",user);
        Object value = redisTemplate.opsForValue().get("user");
        System.out.println("value:" + value);
    }

}
