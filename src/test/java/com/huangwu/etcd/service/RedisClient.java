package com.huangwu.etcd.service;

import com.huangwu.util.SpringContextUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Package: com.huangwu.etcd.service
 * @Author: huangwu
 * @Date: 2018/6/4 9:03
 * @Description:
 * @LastModify:
 */
public class RedisClient {
    List<RedisKey> keys;
    long normalCheckInterval = 8000;

    interface RedisKeyChangeListener {
        void onRedisKeyChange(String key, Object Value);
    }

    CopyOnWriteArrayList<RedisKeyChangeListener> listeners = new CopyOnWriteArrayList<>();
    class WatchThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                watchKeys();
            }
            System.out.println("interrupt...");
        }
        public void watchKeys() {
            long now = System.currentTimeMillis();
            for (RedisKey redisKey : keys) {
                long t = now - redisKey.lastCheckTime;
                boolean isAvailable = redisKey.isAvailable;
                if (isAvailable && t >= normalCheckInterval) {
                    redisKey.checkKeyAvailable();
                    if (redisKey.isAvailable) {
                        for (RedisKeyChangeListener listener : listeners) {
                            listener.onRedisKeyChange(redisKey.key,isAvailable);
                        }
                    }
                }
            }
        }
    }

    WatchThread watchThread;

    public synchronized WatchThread getWatchThread() {
        if (watchThread == null) {
            watchThread = new WatchThread();
            watchThread.start();
        }
        return watchThread;
    }

    public RedisClient(List<RedisKey> keys) {
        this.keys = keys;
    }

    public void addListeners(RedisKeyChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
}

class RedisKey {
    String key;
    boolean isAvailable = true;
    long lastCheckTime = 0;
    Object value;
    private RedisTemplate redisTemplate = SpringContextUtil.getBean("redisTemplate", RedisTemplate.class);

    public boolean checkKeyAvailable() {
        lastCheckTime = System.currentTimeMillis();
        isAvailable = redisTemplate.hasKey(key);
//        if (isAvailable) {
//            value = redisTemplate.opsForValue().get(key);
//        }
        System.out.println("checkKey,key:" + key + ", value:" + value);
        return isAvailable;
    }

    public RedisKey(String key) {
        this.key = key;
    }
}
