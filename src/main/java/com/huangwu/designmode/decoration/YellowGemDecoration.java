package com.huangwu.designmode.decoration;

/**
 * 黄宝石装饰者
 *
 * @Package: com.huangwu.designmode.decoration
 * @Author: huangwu
 * @Date: 2018/7/11 17:31
 * @Description:
 * @LastModify:
 */
public class YellowGemDecoration implements IEquipDecoration {
    private IEquip equip;

    public YellowGemDecoration(IEquip equip) {
        this.equip = equip;
    }

    @Override
    public int calculateAttacK() {
        return 15 + equip.calculateAttacK();
    }

    @Override
    public String description() {
        return equip.description() + "+ 黄宝石";
    }
}
