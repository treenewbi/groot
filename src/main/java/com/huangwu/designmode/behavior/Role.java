package com.huangwu.designmode.behavior;

/**
 * @Package: com.huangwu.designmode.behavior
 * @Author: huangwu
 * @Date: 2018/7/11 21:57
 * @Description:
 * @LastModify:
 */
public abstract class Role {

    protected String name;
    protected IAttackBehavior attackBehavior;
    protected IRunBehavior runBehavior;
    protected IDefenseBehavior defenseBehavior;

    /**
     * 此处的set方法通过返回Role对象，角色对象可以连续调用set来设置多个技术属性，而不需要';'隔开
     *
     * @param attackBehavior
     * @return
     */
    public Role setAttackBehavior(IAttackBehavior attackBehavior) {
        this.attackBehavior = attackBehavior;
        return this;
    }

    public Role setRunBehavior(IRunBehavior runBehavior) {
        this.runBehavior = runBehavior;
        return this;
    }

    public Role setDefenseBehavior(IDefenseBehavior defenseBehavior) {
        this.defenseBehavior = defenseBehavior;
        return this;
    }

    protected void attack() {
        attackBehavior.attack();
    }

    protected void run() {
        runBehavior.run();
    }

    protected void defense() {
        defenseBehavior.defense();
    }

    public Role(String name) {
        this.name = name;
    }
}
