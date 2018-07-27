package com.huangwu.redis;

/**
 * 定义一个消息接收接口
 *
 * @Package: com.huangwu.redis
 * @Author: huangwu
 * @Date: 2018/6/1 12:05
 * @Description:
 * @LastModify:
 */
public interface Receiver {
    /**
     * 处理消息
     *
     * @param message
     * @throws Exception
     */
    void handleMessage(String message) throws Exception;
}
