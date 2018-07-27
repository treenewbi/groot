package com.huangwu.etcd.other;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

/**
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/27 13:17
 * @Description:
 * @LastModify:
 */
public class ErrorLogTest {
    private static File file = new File("D:\\home\\groot-error.log.2018-06-02.log");
    private static File file2 = new File("D:\\home\\copy.log");

    @Test
    public void findException() throws Exception {
        String findStr = "springframework";
        FileInputStream in = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String msg = null;
        ArrayList<String> errors = new ArrayList<>();
        int index = 0;
        int count = 0;
        while ((msg = reader.readLine()) != null) {
            if (msg.contains("Listener execution failed")) {
                errors.add(msg);
            }
            while ((index = msg.indexOf(findStr, index)) != -1) {
                index = index + findStr.length();
                count++;
            }
        }
        System.out.println("日志读取结束...");
        for (String error : errors) {
            System.out.println(error);
        }
        System.out.println("共搜到日志" + errors.size() + "条");
        System.out.println("共出现lianlian: " + count + "次");
    }

    @Test
    public void copyLogTest() throws Exception {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "utf8"));
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
                writer.write(msg + "\n");
                writer.flush();
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        }

    }
}
