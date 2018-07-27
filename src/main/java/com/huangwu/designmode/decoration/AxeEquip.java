package com.huangwu.designmode.decoration;

/**
 * 暗影战斧装备
 *
 * @Package: com.huangwu.designmode.decoration
 * @Author: huangwu
 * @Date: 2018/7/11 17:25
 * @Description:
 * @LastModify:
 */
public class AxeEquip implements IEquip {
    @Override
    public int calculateAttacK() {
        return 25;
    }

    @Override
    public String description() {
        return "暗影战斧";
    }
}
