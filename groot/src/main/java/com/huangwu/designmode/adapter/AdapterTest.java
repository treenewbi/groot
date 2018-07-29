package com.huangwu.designmode.adapter;

/**
 * 1、适配器模式的要点就是把适配器需要实现的接口定义出来
 * 2、在原先的代码中调用适配器父接口
 * 3、实现具体的适配器，该适配器需要引用原先的接口，然后通过适配逻辑实现整个功能的适配
 *
 * @Package: com.huangwu.designmode.adapter
 * @Author: huangwu
 * @Date: 2018/7/11 14:23
 * @Description:
 * @LastModify:
 */
public class AdapterTest {

    public static void main(String[] args) {
        Power power = new PowerAdapter(new NormalPower());
        Mobile mobile = new Mobile();
        mobile.inputPower(power);
    }
}
