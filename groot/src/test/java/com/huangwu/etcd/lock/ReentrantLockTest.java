package com.huangwu.etcd.lock;

import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Package: com.huangwu.etcd.lock
 * @Author: huangwu
 * @Date: 2018/6/6 13:11
 * @Description:
 * @LastModify:
 */
public class ReentrantLockTest {

    private Lock fairLock = new ReentrantLock2(true);
    private Lock unfairLock = new ReentrantLock2();

    @Test
    public void fairTest() throws InterruptedException {
        System.out.println("fair test ....");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new MyTask(fairLock)) {
                @Override
                public String toString() {
                    return getName();
                }
            };
            thread.setName("" + i);
            thread.start();
        }
        Thread.sleep(6000);
    }

    private class MyTask implements Runnable {
        private Lock lock;

        public MyTask(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    System.out.println("lock by:" + Thread.currentThread().getName() +
                            " and " + ((ReentrantLock2) lock).getQueuedThreads() + " waits...");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2() {
        }

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            return super.getQueuedThreads();
        }
    }
}
