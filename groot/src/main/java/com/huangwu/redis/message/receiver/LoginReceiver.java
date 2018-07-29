package com.huangwu.redis.message.receiver;

import com.huangwu.redis.Receiver;
import com.huangwu.redis.Receiver;

/**
 * @Package: com.huangwu.redis.message.receiver
 * @Author: huangwu
 * @Date: 2018/6/2 13:37
 * @Description:
 * @LastModify:
 */
public class LoginReceiver implements Receiver {
    @Override
    public void handleMessage(String message) throws Exception {
        System.out.println("LoginReceiver:" + message);
    }
}
