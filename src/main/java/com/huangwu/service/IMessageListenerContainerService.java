package com.huangwu.service;

import com.huangwu.redis.Receiver;

import java.util.List;

/**
 * 提供对RedismessagelistenerContainer的操作
 *
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/6/2 15:15
 * @Description:
 * @LastModify:
 */
public interface IMessageListenerContainerService {

    /**
     * 向container中添加listener，订阅一个主题，默认监听方法为handleMessage
     *
     * @param receiver
     * @param topic
     */
    void addMessageListener(Receiver receiver, String topic);

    /**
     * 向container中添加listener，订阅一个主题
     *
     * @param receiver
     * @param topic
     * @param listenerMethod 调用的监听方法
     */
    void addMessageListener(Receiver receiver, String topic, String listenerMethod);

    /**
     * 向container中添加listener，订阅多个主题，默认监听方法为handleMessage
     *
     * @param receiver
     * @param topics
     */
    void addMessageListener(Receiver receiver, List<String> topics);

    /**
     * 向container中添加listener，订阅多个主题
     *
     * @param receiver
     * @param topics
     * @param listenerMethod 调用的监听方法
     */
    void addMessageListener(Receiver receiver, List<String> topics, String listenerMethod);

    /**
     * 移除监听
     *
     * @param receiver
     */
    void removeMessageListener(Receiver receiver);

}
