package com.huangwu.etcd.redis;

import com.huangwu.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.huangwu.etcd.redis
 * @Author: huangwu
 * @Date: 2018/6/4 19:56
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class RedisStringOptionsTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet() {
        redisTemplate.opsForValue().set("name", "tom");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }

    @Test
    public void testSet2() throws InterruptedException {
        redisTemplate.opsForValue().set("name2", "zhansgan", 5, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("name2"));
        Thread.sleep(6000);
        System.out.println("name2:" + redisTemplate.opsForValue().get("name2"));
    }

    @Test
    public void testSet3() {
        redisTemplate.opsForValue().set("key", "hello world");
        redisTemplate.opsForValue().set("key", "redis", 6);
        System.out.println("****" + redisTemplate.opsForValue().get("key"));
    }

    @Test
    public void setIfAbsentTest() {
        System.out.println(redisTemplate.opsForValue().setIfAbsent("key", "11"));
        System.out.println(redisTemplate.opsForValue().setIfAbsent("key1", "111"));
    }

    @Test
    public void multiSetTest() {
        Map<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        redisTemplate.opsForValue().multiSet(map);
        ArrayList list = new ArrayList();
        list.add("k1");
        list.add("k2");
        list.add("k3");
        System.out.println(redisTemplate.opsForValue().multiGet(list));
    }

    /**
     * map存在完全一样的才会失败
     */
    @Test
    public void multiSetIfAbsentTest() {
        Map<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        Map<String, String> map2 = new HashMap<>();
        map.put("k4", "v1");
        map.put("k5", "v2");
        map.put("k3", "v3");
        Map<String, String> map3 = new HashMap<>();
        map.put("kk1", "v1");
        map.put("kk2", "v2");
        map.put("kk3", "v3");
        System.out.println(redisTemplate.opsForValue().multiSetIfAbsent(map));
        System.out.println(redisTemplate.opsForValue().multiSetIfAbsent(map2));
        System.out.println(redisTemplate.opsForValue().multiSetIfAbsent(map3));
    }

    @Test
    public void getAndSetTest() {
        redisTemplate.opsForValue().set("ky1", "123");
        System.out.println(redisTemplate.opsForValue().getAndSet("ky1", "456"));
    }

    @Test
    public void incrementTest() {
        redisTemplate.opsForValue().increment("inc", 1.5);
        System.out.println(redisTemplate.opsForValue().get("inc"));
    }

    @Test
    public void appendTest() {
        stringRedisTemplate.opsForValue().append("appenttest2","hello");
        System.out.println(stringRedisTemplate.opsForValue().get("appenttest2"));
        stringRedisTemplate.opsForValue().append("appenttest2"," world");
        System.out.println(stringRedisTemplate.opsForValue().get("appenttest2"));
    }

    @Test
    public void getTest() {
        //读取指定index的字符
        System.out.println(stringRedisTemplate.opsForValue().get("appenttest2",3,-1));
    }

    @Test
    public void sizeTest() {
        System.out.println(stringRedisTemplate.opsForValue().size("appenttest2"));
    }
}
