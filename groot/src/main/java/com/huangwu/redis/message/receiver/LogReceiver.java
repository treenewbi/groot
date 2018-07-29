package com.huangwu.redis.message.receiver;

import com.huangwu.redis.Receiver;
import com.huangwu.redis.Receiver;
import org.springframework.stereotype.Component;

/**
 * 日志接收者
 *
 * @Package: com.huangwu.redis.message
 * @Author: huangwu
 * @Date: 2018/6/1 12:36
 * @Description:
 * @LastModify:
 */
@Component
public class LogReceiver implements Receiver {
    @Override
    public void handleMessage(String message) throws Exception {
        System.out.println("message:" + message);
    }
}
