package com.huangwu.designmode.command;

/**
 * @Package: com.huangwu.designmode.command
 * @Author: huangwu
 * @Date: 2018/7/23 10:18
 * @Description:
 * @LastModify:
 */
public class LightOnCommand implements Command {

    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() throws Exception {
        light.off();
    }
}
