package com.huangwu.designmode.command;

/**
 * @Package: com.huangwu.designmode.command
 * @Author: huangwu
 * @Date: 2018/7/23 10:36
 * @Description:
 * @LastModify:
 */
public class CommandTest {

    public static void main(String[] args) throws Exception {
        Light light = new Light();
        Door door = new Door();
        Computer computer = new Computer();
        ControlPanel controlPanel = new ControlPanel();
        controlPanel.setCommand(0, new DoorOpenCommand(door));
        controlPanel.setCommand(1, new LightOffCommand(light));
        controlPanel.setCommand(2, new DoorCloseCommand(door));
        controlPanel.setCommand(3, new ComputerOnCommand(computer));

        controlPanel.quickPress();

    }
}
