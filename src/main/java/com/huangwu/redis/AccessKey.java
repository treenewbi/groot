package com.huangwu.redis;

/**
 * @Package: com.huangwu.redis
 * @Author: huangwu
 * @Date: 2018/6/3 14:24
 * @Description:
 * @LastModify:
 */
public class AccessKey extends BasePrefix {
    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public AccessKey(String prefix) {
        super(prefix);
    }

    public static AccessKey withExpire(int expire) {
        return new AccessKey(expire, "access");
    }
}
