package com.huangwu.redis.message.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @Package: com.huangwu.redis.message.sender
 * @Author: huangwu
 * @Date: 2018/6/2 12:29
 * @Description:
 * @LastModify:
 */
@EnableScheduling
@Component
public class LogMessageSender {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        stringRedisTemplate.convertAndSend("modifyLog", "操作日志 " + Math.random());
    }
}
