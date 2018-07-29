package com.huangwu.designmode.command;

/**
 * @Package: com.huangwu.designmode.command
 * @Author: huangwu
 * @Date: 2018/7/23 10:19
 * @Description:
 * @LastModify:
 */
public class DoorOpenCommand implements Command {

    private Door door;

    public DoorOpenCommand(Door door) {
        this.door = door;
    }

    @Override
    public void execute() throws Exception {
        door.open();
    }
}
