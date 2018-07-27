package com.huangwu.etcd.other;

/**
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/26 19:38
 * @Description:
 * @LastModify:
 */
public class PrintTask extends Thread {
    private static volatile int num = 0;
    private int threadId;

    public PrintTask(int threadId) {
        this.threadId = threadId;
    }

    /**
     * 问题描述
     * 启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5, 然后是线程2打印6,7,8,9,10,
     * 然后是线程3打印11,12,13,14,15. 接着再由线程1打印16,17,18,19,20….以此类推, 直到打印到75.
     */

    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            new PrintTask(i).start();
        }
    }

    @Override
    public void run() {
        while (num < 75) {
            synchronized (PrintTask.class) {
                if (num / 5 % 3 + 1 == threadId) {
                    System.out.print("线程" + threadId + ":");
                    for (int i = 0; i < 5; i++) {
                        System.out.print(++num);
                    }
                    System.out.println();
                    PrintTask.class.notifyAll();
                } else {
                    try {
                        PrintTask.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
