package com.huangwu.designmode.adapter;

/**
 * 手机类
 *
 * @Package: com.huangwu.designmode.adapter
 * @Author: huangwu
 * @Date: 2018/7/11 14:13
 * @Description:
 * @LastModify:
 */
public class Mobile {

    public void inputPower(Power power) {
        int p = power.providePower();
        System.out.println("手机正在充电，当前电压是：" + p + "V");
    }
}
