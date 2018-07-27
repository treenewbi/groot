package com.huangwu.service.impl;

import com.huangwu.common.ErrorCode;
import com.huangwu.domain.vo.SeckillUserVo;
import com.huangwu.exception.GlobalException;
import com.huangwu.service.ISeckillService;
import com.huangwu.util.MD5Util;
import com.huangwu.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.huangwu.redis.SeckillKey.seckillPath;
import static com.huangwu.redis.SeckillKey.seckillVerifyCode;


/**
 * @Package: com.huangwu.service.impl
 * @Author: huangwu
 * @Date: 2018/6/2 21:29
 * @Description:
 * @LastModify:
 */
@Service
public class SeckillService implements ISeckillService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String createVerifyCode(SeckillUserVo userVo, long goodsId) throws Exception {
        if (userVo == null || goodsId <= 0)
            throw new GlobalException(ErrorCode.SECKILL_PARAM_ERROR);
        String verifyCode = StringHelper.getVerifyCode();
        int value = StringHelper.calculateVarifyCode(verifyCode);
        // 结果值存入redis
        redisTemplate.opsForValue().set(seckillVerifyCode.realKey(userVo.getUserName() + "_" + goodsId), value, seckillVerifyCode.expireSeconds(), TimeUnit.SECONDS);
        return verifyCode;
    }

    @Override
    public boolean checkVerifyCode(SeckillUserVo userVo, long goodsId, int verifyCode) throws Exception {
        int val = (Integer) redisTemplate.opsForValue().get(seckillVerifyCode.realKey(userVo + "_" + goodsId));
        if (verifyCode != val)
            return false;
        redisTemplate.delete(seckillVerifyCode.realKey(userVo + "_" + goodsId));
        return true;
    }

    @Override
    public String createSeckillPath(SeckillUserVo userVo, long goodsId) throws Exception {
        String str = MD5Util.md5(StringHelper.getUUID() + "123456");
        redisTemplate.opsForValue().set(seckillPath.realKey(userVo.getUserId() + "_" + goodsId), str, seckillPath.expireSeconds(), TimeUnit.SECONDS);
        return str;
    }

    @Override
    public boolean checkPath(SeckillUserVo userVo, long goodsId) throws Exception {
//        String str =
        return false;
    }
}
