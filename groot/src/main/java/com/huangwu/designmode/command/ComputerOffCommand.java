package com.huangwu.designmode.command;

/**
 * @Package: com.huangwu.designmode.command
 * @Author: huangwu
 * @Date: 2018/7/23 10:21
 * @Description:
 * @LastModify:
 */
public class ComputerOffCommand implements Command {

    private Computer computer;

    public ComputerOffCommand(Computer computer) {
        this.computer = computer;
    }

    @Override
    public void execute() throws Exception {
        computer.off();
    }
}
