package com.huangwu.designmode.decoration;

/**
 * @Package: com.huangwu.designmode.decoration
 * @Author: huangwu
 * @Date: 2018/7/11 17:36
 * @Description:
 * @LastModify:
 */
public class RedGemDecoration implements IEquipDecoration {
    private IEquip equip;

    public RedGemDecoration(IEquip equip) {
        this.equip = equip;
    }

    @Override
    public int calculateAttacK() {
        return 25 + equip.calculateAttacK();
    }

    @Override
    public String description() {
        return equip.description() + "+红宝石";
    }
}
