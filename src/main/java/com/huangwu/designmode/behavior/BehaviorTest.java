package com.huangwu.designmode.behavior;

/**
 * 策略模式（Strategy Pattern）：定义了算法族，分别封装起来，让它们之间可相互替换，此模式让算法的变化独立于使用算法的客户。
 *
 * @Package: com.huangwu.designmode.behavior
 * @Author: huangwu
 * @Date: 2018/7/11 22:05
 * @Description:
 * @LastModify:
 */
public class BehaviorTest {
    public static void main(String[] args) {
        Role role = new RoleA("远程攻击角色");

        role.setAttackBehavior(new AttackA())
                .setDefenseBehavior(new DefenseA())
                .setRunBehavior(new RunA());
        role.run();
        role.attack();
        role.defense();
    }
}
