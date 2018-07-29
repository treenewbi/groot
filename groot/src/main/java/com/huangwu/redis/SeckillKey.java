package com.huangwu.redis;

import com.huangwu.redis.BasePrefix;

/**
 * @Package: com.huangwu.redis.message
 * @Author: huangwu
 * @Date: 2018/6/3 9:14
 * @Description:
 * @LastModify:
 */
public class SeckillKey extends BasePrefix {
    public SeckillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SeckillKey seckillVerifyCode = new SeckillKey(300,"vc");
    public static SeckillKey seckillPath = new SeckillKey(300,"pa");
}
