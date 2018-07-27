package com.huangwu.designmode.command;

/**
 * @Package: com.huangwu.designmode.command
 * @Author: huangwu
 * @Date: 2018/7/23 10:17
 * @Description:
 * @LastModify:
 */
public class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() throws Exception {
        light.off();
    }
}
