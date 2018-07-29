package com.huangwu.redis;

/**
 * @Package: com.huangwu.redis
 * @Author: huangwu
 * @Date: 2018/5/31 10:29
 * @Description:
 * @LastModify:
 */
public abstract class BasePrefix implements KeyPrefix {
    private int expireSeconds;

    private String prefix;

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        this.prefix = prefix;
        this.expireSeconds = 0;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix + ":";
    }

    @Override
    public String realKey(String key) {
        return getPrefix() + key;
    }
}
