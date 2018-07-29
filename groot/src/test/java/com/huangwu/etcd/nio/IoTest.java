package com.huangwu.etcd.nio;

import org.junit.Test;

import java.io.*;

/**
 * @Package: com.huangwu.etcd.nio
 * @Author: huangwu
 * @Date: 2018/7/18 15:15
 * @Description:
 * @LastModify:
 */
public class IoTest {
    /**
     * 使用常规的阻塞IO实现文件读取
     */
    @Test
    public void printFile() {
        File file = new File("F:\\niotest.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String msg = null;
            while ((msg = reader.readLine()) != null) {
                System.out.println(msg);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    @Test
    public void ioBlockTest() {
        File file = new File("F:\\block.txt");
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] buff = new byte[1024];
            int val = in.read();
            System.out.println("val: " + val);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
