package com.huangwu.etcd.other;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Package: com.huangwu.etcd.othertest
 * @Author: huangwu
 * @Date: 2018/6/22 10:10
 * @Description:
 * @LastModify:
 */
public class StateTask implements Callable<String> {

    private CountDownLatch latch;
    private int taskId;

    public StateTask(CountDownLatch latch, AtomicInteger taskId) {
        this.latch = latch;
        this.taskId = taskId.incrementAndGet();
    }

    @Override
    public String call() throws Exception {
        int num = 0;
        while (!Thread.currentThread().isInterrupted()) {
            num++;
            TimeUnit.SECONDS.sleep(1);
            if (taskId != 5) {
                latch.countDown();
                System.out.println(Thread.currentThread().getName() + " execute taskID：" + taskId + " succeed");
                return "SUCCEED";
            }
            System.out.println(Thread.currentThread().getName() + " execute taskID：" + taskId + " running.....");
        }
        return null;
    }
}
