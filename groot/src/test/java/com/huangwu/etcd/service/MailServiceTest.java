package com.huangwu.etcd.service;

import com.huangwu.MainApplication;
import com.huangwu.redis.EmailKey;
import com.huangwu.service.IEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static com.huangwu.redis.EmailKey.emailVerifyCodeKey;

/**
 * 邮件服务测试类
 *
 * @Package: com.huangwu.etcd.service
 * @Author: huangwu
 * @Date: 2018/5/29 13:26
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class MailServiceTest {

    @Resource
    private IEmailService mailService;

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testSendSimpleEmail() throws Exception {
//        mailService.sendSimpleEmail("314303732@qq.com", "test send mail", "this is test content");
        System.out.println(111);
    }

    @Test
    public void testGet() {
//        redisTemplate.opsForValue().set(emailVerifyCodeKey.realKey("314303732@qq.com"),"1357",emailVerifyCodeKey.expireSeconds());
//        ValueOperations<String,String> opt = redisTemplate.opsForValue();
        Object value = redisTemplate.opsForValue().get(emailVerifyCodeKey.realKey("314303732@qq.com"));
    }

}
