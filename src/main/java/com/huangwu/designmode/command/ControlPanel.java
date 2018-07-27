package com.huangwu.designmode.command;

/**
 * @Package: com.huangwu.designmode.command
 * @Author: huangwu
 * @Date: 2018/7/23 10:23
 * @Description:
 * @LastModify:
 */
public class ControlPanel {
    private static final int CONTROL_SIZE = 9;
    private Command[] commands;

    /**
     * 默认空命令class，不执行任何命令
     */
    private class NoCommand implements Command {

        @Override
        public void execute() {

        }
    }

    public ControlPanel() {
        this.commands = new Command[CONTROL_SIZE];
        for (int i = 0; i < CONTROL_SIZE; i++) {
            commands[i] = new NoCommand();
        }
    }

    public void setCommand(int index, Command command) {
        commands[index] = command;
    }

    public void keyPress(int index) throws Exception {
        if (index < 0 || index > CONTROL_SIZE)
            throw new Exception();
        commands[index].execute();
    }

    public void quickPress() throws Exception {
        for (Command command : commands) {
            command.execute();
        }
    }
}

