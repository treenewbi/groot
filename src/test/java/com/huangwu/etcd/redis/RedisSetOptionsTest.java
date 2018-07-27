package com.huangwu.etcd.redis;

import com.huangwu.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Package: com.huangwu.etcd.redis
 * @Author: huangwu
 * @Date: 2018/6/6 8:39
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class RedisSetOptionsTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void addTest() {
        redisTemplate.opsForSet().add("set", "s1", "s2");
        String[] str = new String[]{"s3", "s4", "s5"};
        redisTemplate.opsForSet().add("set", str);
        System.out.println(redisTemplate.opsForSet().members("set"));
    }

    @Test
    public void removeTest() {
        System.out.println(redisTemplate.opsForSet().members("set"));
        redisTemplate.opsForSet().remove("set", "s1");
        System.out.println(redisTemplate.opsForSet().members("set"));
    }

    @Test
    public void popTest() {
        System.out.println(redisTemplate.opsForSet().members("set"));
        System.out.println(redisTemplate.opsForSet().pop("set"));
        System.out.println(redisTemplate.opsForSet().members("set"));
    }

    @Test
    public void moveTest() {
        redisTemplate.opsForSet().move("set", "s2", "set2");
        System.out.println(redisTemplate.opsForSet().members("set"));
        System.out.println(redisTemplate.opsForSet().members("set2"));
    }

    @Test
    public void isMemberTest() {
        System.out.println(redisTemplate.opsForSet().isMember("set", "s3"));
        System.out.println(redisTemplate.opsForSet().isMember("set", "s32"));
    }

    @Test
    public void intersectTest() {
        System.out.println(redisTemplate.opsForSet().members("set"));
        System.out.println(redisTemplate.opsForSet().members("set2"));
        System.out.println(redisTemplate.opsForSet().intersect("set", "set2"));
        redisTemplate.opsForSet().add("set2", "s5", "s3", "s6");
    }

    @Test
    public void intersectAndStoreTest() {
        System.out.println(redisTemplate.opsForSet().members("set"));
        System.out.println(redisTemplate.opsForSet().members("set2"));
        redisTemplate.opsForSet().intersectAndStore("set", "set2", "set3");
        System.out.println(redisTemplate.opsForSet().members("set3"));
    }

    @Test
    public void unionTest() {
        System.out.println(redisTemplate.opsForSet().union("set", "set2"));
    }

    @Test
    public void unionAndStoreTest() {
        redisTemplate.opsForSet().unionAndStore("set", "set2", "set3");
        System.out.println(redisTemplate.opsForSet().members("set3"));
        System.out.println(redisTemplate.opsForSet().members("set2"));
        System.out.println(redisTemplate.opsForSet().members("set"));
    }

    @Test
    public void differenceTest() {
        System.out.println(redisTemplate.opsForSet().difference("set", "set2"));
    }

    @Test
    public void differenceAndStoreTest() {
        redisTemplate.opsForSet().differenceAndStore("set", "set2", "set4");
        System.out.println(redisTemplate.opsForSet().members("set"));
        System.out.println(redisTemplate.opsForSet().members("set2"));
        System.out.println(redisTemplate.opsForSet().members("set4"));
        System.out.println(redisTemplate.opsForSet().difference("set", "set2"));
    }

    @Test
    public void randomMemberTest() {
        System.out.println(redisTemplate.opsForSet().members("set"));
        System.out.println(redisTemplate.opsForSet().randomMember("set"));
        System.out.println(redisTemplate.opsForSet().members("set"));
    }

    @Test
    public void distinctRandomMembersTest() {
        System.out.println(redisTemplate.opsForSet().distinctRandomMembers("set", 3));
    }

    @Test
    public void scanTest() {
        Cursor cursor = redisTemplate.opsForSet().scan("set", ScanOptions.NONE);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
}
