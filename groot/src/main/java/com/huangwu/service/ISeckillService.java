package com.huangwu.service;

import com.huangwu.domain.vo.SeckillUserVo;

import java.awt.image.BufferedImage;

/**
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/6/2 21:27
 * @Description:
 * @LastModify:
 */
public interface ISeckillService {
    /**
     * 生成一个随机图形验证码
     *
     * @param userVo
     * @param goodsId
     * @return
     * @throws Exception
     */
    String createVerifyCode(SeckillUserVo userVo, long goodsId) throws Exception;

    /**
     * 校验验证码
     *
     * @param userVo
     * @param goodsId
     * @param verifyCode
     * @return
     * @throws Exception
     */
    boolean checkVerifyCode(SeckillUserVo userVo, long goodsId, int verifyCode) throws Exception;

    /**
     * 创建秒杀接口URL
     *
     * @param userVo
     * @param goodsId
     * @return
     * @throws Exception
     */
    String createSeckillPath(SeckillUserVo userVo, long goodsId) throws Exception;

    /**
     * 校验秒杀URL
     *
     * @param userVo
     * @param goodsId
     * @return
     * @throws Exception
     */
    boolean checkPath(SeckillUserVo userVo, long goodsId) throws Exception;
}
