package com.huangwu.etcd.other;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/27 14:40
 * @Description:
 * @LastModify:
 */
public class LogWriterTask extends Thread {
    private File file;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public LogWriterTask(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (file == null) {
                throw new IllegalStateException("logFile can not be null!");
            }
            Writer writer;
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                writer = new FileWriter(file, true);
                writer.write(dateFormat.format(new Date()) + "  Listener execution failed " + "\n");
                writer.flush();
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
