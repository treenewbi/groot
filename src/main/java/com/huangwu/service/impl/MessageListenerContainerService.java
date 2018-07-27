package com.huangwu.service.impl;

import com.huangwu.redis.Receiver;
import com.huangwu.service.IMessageListenerContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.huangwu.service.impl
 * @Author: huangwu
 * @Date: 2018/6/2 15:24
 * @Description:
 * @LastModify:
 */
@Service
public class MessageListenerContainerService implements IMessageListenerContainerService {

    private final String DEFAULT_LISTENER_METHOD = "handlerMessage";

    @Autowired
    private RedisMessageListenerContainer container;

    @Override
    public void addMessageListener(Receiver receiver, String topic) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver);
        adapter.afterPropertiesSet();
        container.addMessageListener(adapter, new PatternTopic(topic));
    }

    @Override
    public void addMessageListener(Receiver receiver, String topic, String listenerMethod) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, listenerMethod);
        adapter.afterPropertiesSet();
        container.addMessageListener(adapter, new PatternTopic(topic));
    }

    @Override
    public void addMessageListener(Receiver receiver, List<String> topics) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver);
        adapter.afterPropertiesSet();
        ArrayList<PatternTopic> patternTopics = new ArrayList<>();
        for (String topic : topics) {
            PatternTopic patternTopic = new PatternTopic(topic);
            patternTopics.add(patternTopic);
        }
        container.addMessageListener(adapter, patternTopics);
    }

    @Override
    public void addMessageListener(Receiver receiver, List<String> topics, String listenerMethod) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, listenerMethod);
        adapter.afterPropertiesSet();
        ArrayList<PatternTopic> patternTopics = new ArrayList<>();
        for (String topic : topics) {
            PatternTopic patternTopic = new PatternTopic(topic);
            patternTopics.add(patternTopic);
        }
        container.addMessageListener(adapter, patternTopics);
    }

    @Override
    public void removeMessageListener(Receiver receiver) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver);
        container.removeMessageListener(adapter,new PatternTopic("modifyLog"));
    }
}
