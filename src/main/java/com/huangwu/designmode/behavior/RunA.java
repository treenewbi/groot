package com.huangwu.designmode.behavior;

/**
 * @Package: com.huangwu.designmode.behavior
 * @Author: huangwu
 * @Date: 2018/7/11 22:05
 * @Description:
 * @LastModify:
 */
public class RunA implements IRunBehavior {
    @Override
    public void run() {
        System.out.println("凌波微步");
    }
}
