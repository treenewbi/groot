package com.huangwu.designmode.decoration;

/**
 * 装饰者模式
 * 1、被装饰者和装饰者需要实现共同的接口
 * 2、装饰者内部需要有父接口的成员变量，且构造方法需要传入这个参数
 * 3、在装饰者内部对被装饰者进行装饰
 *
 * @Package: com.huangwu.designmode.decoration
 * @Author: huangwu
 * @Date: 2018/7/11 17:37
 * @Description:
 * @LastModify:
 */
public class DecorationTest {
    public static void main(String[] args) {
        ShoeEquip shoeEquip = new ShoeEquip();
        System.out.println(shoeEquip.description() + "的攻击力是：" + shoeEquip.calculateAttacK());

        AxeEquip axeEquip = new AxeEquip();
        System.out.println(axeEquip.description() + "的攻击力是：" + axeEquip.calculateAttacK());

        IEquip equipShoe = new RedGemDecoration(new YellowGemDecoration(new BuleGemDecoration(new ShoeEquip())));
        System.out.println(equipShoe.description() + "的攻击力是：" + equipShoe.calculateAttacK());

        IEquip equipAxe = new RedGemDecoration(new YellowGemDecoration(new AxeEquip()));
        System.out.println(equipAxe.description() + "的攻击力是：" + equipAxe.calculateAttacK());
    }
}
