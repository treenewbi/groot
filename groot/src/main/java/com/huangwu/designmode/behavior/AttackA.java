package com.huangwu.designmode.behavior;

/**
 * @Package: com.huangwu.designmode.behavior
 * @Author: huangwu
 * @Date: 2018/7/11 22:03
 * @Description:
 * @LastModify:
 */
public class AttackA implements IAttackBehavior {
    @Override
    public void attack() {
        System.out.println("远程攻击");
    }
}
