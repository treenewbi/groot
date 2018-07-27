package com.huangwu.redis;

/**
 * @Package: com.huangwu.redis
 * @Author: huangwu
 * @Date: 2018/6/3 12:42
 * @Description:
 * @LastModify:
 */
public class UserKey extends BasePrefix {
    //默认两天
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey token = new UserKey(TOKEN_EXPIRE, "tk");
}
