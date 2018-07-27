package com.huangwu.designmode.decoration;

/**
 * 急速靴装备
 *
 * @Package: com.huangwu.designmode.decoration
 * @Author: huangwu
 * @Date: 2018/7/11 17:23
 * @Description:
 * @LastModify:
 */
public class ShoeEquip implements IEquip {
    @Override
    public int calculateAttacK() {
        return 15;
    }

    @Override
    public String description() {
        return "急速靴";
    }
}
