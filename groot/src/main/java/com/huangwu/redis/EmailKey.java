package com.huangwu.redis;

import org.apache.commons.lang3.StringUtils;

/**
 * 邮件相关key
 *
 * @Package: com.huangwu.redis
 * @Author: huangwu
 * @Date: 2018/5/31 10:32
 * @Description:
 * @LastModify:
 */
public class EmailKey extends BasePrefix {

    private EmailKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static final EmailKey emailVerifyCodeKey = new EmailKey(300, "vc");

}
