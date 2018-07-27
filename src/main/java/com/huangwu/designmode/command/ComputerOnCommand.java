package com.huangwu.designmode.command;

/**
 * @Package: com.huangwu.designmode.command
 * @Author: huangwu
 * @Date: 2018/7/23 10:22
 * @Description:
 * @LastModify:
 */
public class ComputerOnCommand implements Command {
    private Computer computer;

    public ComputerOnCommand(Computer computer) {
        this.computer = computer;
    }

    @Override
    public void execute() throws Exception {
        computer.on();
    }
}
