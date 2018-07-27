package com.huangwu.etcd.other;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/27 14:52
 * @Description:
 * @LastModify:
 */
public class LogTest {
    public static void main(String[] args) throws InterruptedException {
        File file = new File("D:\\home\\groot-error.log.2018-06-02.log");
        LogWriterTask writerTask = new LogWriterTask(file);
        LogReaderTask readerTask = new LogReaderTask(file);
        writerTask.start();
        readerTask.start();
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
