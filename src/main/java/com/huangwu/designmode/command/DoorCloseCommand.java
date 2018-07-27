package com.huangwu.designmode.command;

/**
 * @Package: com.huangwu.designmode.command
 * @Author: huangwu
 * @Date: 2018/7/23 10:20
 * @Description:
 * @LastModify:
 */
public class DoorCloseCommand implements Command {
    private Door door;

    public DoorCloseCommand(Door door) {
        this.door = door;
    }

    @Override
    public void execute() throws Exception {
        door.close();
    }
}
