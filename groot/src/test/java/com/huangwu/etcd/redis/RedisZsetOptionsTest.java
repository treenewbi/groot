package com.huangwu.etcd.redis;

import com.huangwu.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * @Package: com.huangwu.etcd.redis
 * @Author: huangwu
 * @Date: 2018/6/6 9:20
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class RedisZsetOptionsTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void addTest() {
        System.out.println(redisTemplate.opsForZSet().add("zset", "z1", 1.1));
        System.out.println(redisTemplate.opsForZSet().add("zset", "z2", 1.0));
        System.out.println(redisTemplate.opsForZSet().add("zset", "z3", 0.3));
        System.out.println(redisTemplate.opsForZSet().range("zset", 0, -1));
    }

    @Test
    public void addTest2() {
        ZSetOperations.TypedTuple<Object> tuple = new DefaultTypedTuple<Object>("z4", 2.0);
        ZSetOperations.TypedTuple<Object> tuple2 = new DefaultTypedTuple<Object>("z5", 2.1);
        Set set = new HashSet();
        set.add(tuple);
        set.add(tuple2);
        redisTemplate.opsForZSet().add("zset3", set);
        System.out.println(redisTemplate.opsForZSet().range("zset3", 0, -1));
    }

    @Test
    public void incrementScodeTest() {
        System.out.println(redisTemplate.opsForZSet().range("zset", 0, -1));
        System.out.println(redisTemplate.opsForZSet().incrementScore("zset", "z2", 2.3));
        System.out.println(redisTemplate.opsForZSet().range("zset", 0, -1));
    }

    @Test
    public void rankTest() {
        System.out.println(redisTemplate.opsForZSet().range("zset", 0, -1));
        System.out.println(redisTemplate.opsForZSet().rank("zset","z5"));
        System.out.println(redisTemplate.opsForZSet().rank("zset","z1"));
    }

    @Test
    public void reverseRangeTest() {
        System.out.println(redisTemplate.opsForZSet().range("zset",0,-1));
        System.out.println(redisTemplate.opsForZSet().reverseRange("zset",0,3));
    }

    @Test
    public void scoreTest() {
        System.out.println(redisTemplate.opsForZSet().score("zset","z2"));
        System.out.println(redisTemplate.opsForZSet().score("zset","z4"));
    }
}
