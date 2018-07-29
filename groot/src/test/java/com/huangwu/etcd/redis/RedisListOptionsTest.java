package com.huangwu.etcd.redis;

import com.huangwu.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.huangwu.etcd.redis
 * @Author: huangwu
 * @Date: 2018/6/5 10:28
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class RedisListOptionsTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void leftPushTest() {
        System.out.println(redisTemplate.opsForList().leftPush("list", "java"));
        System.out.println(redisTemplate.opsForList().leftPush("list", "python"));
        System.out.println(redisTemplate.opsForList().leftPush("list", "php"));
        System.out.println(redisTemplate.opsForList().range("list", 0, -1));
    }

    @Test
    public void trimTest() {
        System.out.println(redisTemplate.opsForList().leftPush("list2", "java"));
        System.out.println(redisTemplate.opsForList().leftPush("list2", "python"));
        System.out.println(redisTemplate.opsForList().leftPush("list2", "php"));
        System.out.println(redisTemplate.opsForList().range("list2", 0, -1));
        redisTemplate.opsForList().trim("list2", 1, -1);
        System.out.println(redisTemplate.opsForList().range("list2", 0, -1));
    }

    @Test
    public void sizeTest() {
        System.out.println(redisTemplate.opsForList().size("list2"));
    }

    @Test
    public void leftPushAllTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("zhangsan");
        list.add("lisi");
        list.add("wangwu");
        redisTemplate.opsForList().leftPushAll("student", list);
        System.out.println(redisTemplate.opsForList().range("student", 0, -1));
    }

    @Test
    public void leftPushIfPresendTest() {
        System.out.println(redisTemplate.opsForList().leftPushIfPresent("ss", "1"));
        System.out.println(redisTemplate.opsForList().leftPushIfPresent("student", "xiaoming"));
    }

    @Test
    public void leftPushTest2() {
        redisTemplate.opsForList().leftPush("student", "lisi1", "laowang2");
        System.out.println(redisTemplate.opsForList().range("student", 0, -1));
    }

    @Test
    public void rightPushTest() {
        redisTemplate.opsForList().rightPush("student", "八嘎");
        System.out.println(redisTemplate.opsForList().range("student", 0, -1));
    }

    @Test
    public void rightPushAllTest() {
        List<String> arr2 = new ArrayList<String>();
        arr2.add("阿猪");
        arr2.add("阿猫");
        arr2.add("阿狗");
        redisTemplate.opsForList().rightPushAll("student", arr2);
        System.out.println(redisTemplate.opsForList().range("student", 0, -1));
    }

    @Test
    public void setTest() {
        redisTemplate.opsForList().set("student", 0, "hello world");
        System.out.println(redisTemplate.opsForList().range("student", 0, -1));
    }

    @Test
    public void removeTest() {
        System.out.println(redisTemplate.opsForList().range("student", 0, -1));
        redisTemplate.opsForList().remove("student", 1, "hello world");
        System.out.println(redisTemplate.opsForList().range("student", 0, -1));
    }

    @Test
    public void indexTest() {
        System.out.println(redisTemplate.opsForList().range("student", 0, -1));
        System.out.println(redisTemplate.opsForList().index("student", 3));
    }

    @Test
    public void leftPopTest() {
        redisTemplate.opsForList().leftPush("pop", "java");
        System.out.println(redisTemplate.opsForList().range("pop", 0, -1));
        redisTemplate.opsForList().leftPop("pop");
        System.out.println(redisTemplate.opsForList().range("pop", 0, -1));
        redisTemplate.opsForList().leftPop("pop", 15000, TimeUnit.SECONDS);

    }

    @Test
    public void rightPopAndLeftPushTest() {
        System.out.println("student:" + redisTemplate.opsForList().range("student", 0, -1));
        System.out.println("pop:" + redisTemplate.opsForList().range("pop", 0, -1));
        redisTemplate.opsForList().rightPopAndLeftPush("student", "pop");
        System.out.println("student:" + redisTemplate.opsForList().range("student", 0, -1));
        System.out.println("pop:" + redisTemplate.opsForList().range("pop", 0, -1));
    }
}
