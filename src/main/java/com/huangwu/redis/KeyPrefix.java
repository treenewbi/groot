package com.huangwu.redis;

/**
 * redis key前缀底层接口
 *
 * @Package: com.huangwu.redis
 * @Author: huangwu
 * @Date: 2018/5/31 10:05
 * @Description:
 * @LastModify:
 */
public interface KeyPrefix {
    /**
     * 过期时间
     *
     * @return
     */
    int expireSeconds();

    /**
     * 获取key的前缀
     *
     * @return
     */
    String getPrefix();

    /**
     * 通过前缀和key构造一个真实key
     *
     * @param key
     * @return
     */
    String realKey(String key);
}
