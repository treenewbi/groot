package com.huangwu.designmode.behavior;

/**
 * @Package: com.huangwu.designmode.behavior
 * @Author: huangwu
 * @Date: 2018/7/11 22:04
 * @Description:
 * @LastModify:
 */
public class DefenseA implements IDefenseBehavior {
    @Override
    public void defense() {
        System.out.println("铁布衫护体");
    }
}
