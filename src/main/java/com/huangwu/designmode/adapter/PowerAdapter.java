package com.huangwu.designmode.adapter;

/**
 * @Package: com.huangwu.designmode.adapter
 * @Author: huangwu
 * @Date: 2018/7/11 14:18
 * @Description:
 * @LastModify:
 */
public class PowerAdapter implements Power {

    private NormalPower normalPower;

    public PowerAdapter(NormalPower normalPower) {
        this.normalPower = normalPower;
    }

    @Override
    public int providePower() {
        int p = normalPower.providePower();
        System.out.println("适配器对电压:" + p + " 进行了手机充电适配...");
        return 5;
    }
}
