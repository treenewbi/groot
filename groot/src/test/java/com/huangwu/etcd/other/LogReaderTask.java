package com.huangwu.etcd.other;


import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/27 13:47
 * @Description:
 * @LastModify:
 */
public class LogReaderTask extends Thread {
    //日志文件
    private File file;
    //上一次访问时文件大小
    private long lastFileSize;
    private AtomicBoolean aBoolean = new AtomicBoolean(true);

    public LogReaderTask(File file) {
        this.file = file;
        this.lastFileSize = file.length();
    }

    /**
     * 开始读取文件
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long lenth = file.length();
            if (aBoolean.compareAndSet(true, false)) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        if (msg.contains("Listener execution failed")) {
                            System.out.println(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (lenth < lastFileSize) {
                System.out.println("logFile is reset...");
                this.lastFileSize = lenth;
            } else if (lenth > lastFileSize) {
                RandomAccessFile accessFile;
                try {
                    accessFile = new RandomAccessFile(file, "r");
                    accessFile.seek(lastFileSize);
                    String msg = null;
                    while ((msg = accessFile.readLine()) != null) {
                        if (msg.contains("Listener execution failed")) {
                            System.out.println(msg);
                        }
                    }
                    lastFileSize = file.length();
                    accessFile.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
