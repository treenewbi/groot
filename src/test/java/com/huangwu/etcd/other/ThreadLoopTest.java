package com.huangwu.etcd.other;

/**
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/26 20:44
 * @Description:
 * @LastModify:
 */
public class ThreadLoopTest extends Thread {
    private static volatile int loopNum = 0;

    public static void mainLoop() {
        while (loopNum < 40) {
            synchronized (ThreadLoopTest.class) {
                if (loopNum / 2 % 2 + 1 == 2) {
                    System.out.print("main线程" + ":");
                    for (int i = 0; i < 2; i++) {
                        System.out.print(++loopNum);
                    }
                    System.out.println();
                    ThreadLoopTest.class.notifyAll();
                } else {
                    try {

                        ThreadLoopTest.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void subLoop() {
        while (loopNum < 40) {
            synchronized (ThreadLoopTest.class) {
                if (loopNum / 2 % 2 + 1 == 1) {
                    System.out.print("子线程" + ":");
                    for (int i = 0; i < 2; i++) {
                        System.out.print(++loopNum);
                    }
                    System.out.println();
                    ThreadLoopTest.class.notifyAll();
                } else {
                    try {
                        ThreadLoopTest.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                subLoop();
            }
        }).start();
        mainLoop();
    }
}
