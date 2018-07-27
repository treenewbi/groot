package com.huangwu.designmode.decoration;

/**
 * 蓝宝石装饰者
 *
 * @Package: com.huangwu.designmode.decoration
 * @Author: huangwu
 * @Date: 2018/7/11 17:28
 * @Description:
 * @LastModify:
 */
public class BuleGemDecoration implements IEquipDecoration {

    private IEquip equip;

    public BuleGemDecoration(IEquip equip) {
        this.equip = equip;
    }

    @Override
    public int calculateAttacK() {
        return 5 + equip.calculateAttacK();
    }

    @Override
    public String description() {
        return equip.description() + "+ 蓝宝石";
    }
}
