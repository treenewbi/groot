package com.huangwu.etcd.other;

/**
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/26 20:08
 * @Description:
 * @LastModify:
 */
public class PrintAbcTask extends Thread {
    private static volatile int printNum = 0;
    private static char[] chars = new char[]{'A', 'B', 'C'};
    private int threadId;

    public PrintAbcTask(int threadId) {
        this.threadId = threadId;
    }

    /**
     * 实例化三个线程，一个线程打印a,一个打印b,一个打印c,三个线程同时执行，要求打印出6个连着的abc
     */
    @Override
    public void run() {
        while (printNum < 18) {
            synchronized (PrintAbcTask.class) {
                if (printNum % 3 + 1 == threadId) {
                    System.out.print(chars[threadId - 1]);
                    printNum++;
                    PrintAbcTask.class.notifyAll();
                } else {
                    try {
                        PrintAbcTask.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new PrintAbcTask(i + 1).start();
        }
    }
}
