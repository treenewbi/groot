package com.huangwu.etcd.other.thread;

import javafx.beans.binding.When;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.huangwu.etcd.other.thread
 * @Author: huangwu
 * @Date: 2018/8/1 20:58
 * @Description:
 * @LastModify:
 */
public class ShutdownHookTest {

    @Test
    public void testSaveWorkQueueWhenJvmShutDowm() throws InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<>(15));
        // 1、创建一个jvm关闭后的钩子线程
        Thread shutdownHook = new Thread(new Runnable() {
            /**
             * 此处模拟打印出未消费的任务，实际业务需要做持久化处理
             */
            @Override
            public void run() {
                BlockingQueue<Runnable> queue = pool.getQueue();
                while (queue.size() > 0) {
                    System.out.println("jvm关闭后未消费的任务：" + queue.poll());
                }
            }
        });

        // 2、循环添加15个任务，pool只有一个线程，且任务中阻塞了一秒
        for (int i = 0; i < 15; i++) {
            pool.execute(new PoolTask(i));
        }

        // 3、添加钩子
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        // 4、5秒钟后主线程退出，jvm关闭，因为存在运行时间查，理论上线程池只消费了4-6个，剩下的任务在workQueue中排队
        TimeUnit.SECONDS.sleep(5);
        System.exit(0);

    }

    class PoolTask implements Runnable {
        private int taskNum;

        public PoolTask(int taskNum) {
            this.taskNum = taskNum;
        }

        @Override
        public void run() {
            System.out.println("my taskNum is " + taskNum);
            try {
                // 阻塞一秒，延迟消费速度
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "PoolTask{" +
                    "taskNum=" + taskNum +
                    '}';
        }
    }
}
