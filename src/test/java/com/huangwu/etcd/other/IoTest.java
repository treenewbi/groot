package com.huangwu.etcd.other;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

/**
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/27 20:51
 * @Description:
 * @LastModify:
 */
public class IoTest {
    private ArrayList<String> fileList = new ArrayList<>();
    private ArrayList<String> dirList = new ArrayList<>();

    /**
     * （一） 在电脑D盘下创建一个文件为HelloWorld.txt文件，判断他是文件还是目录，在创建一个目
     * 录IOTest,之后将HelloWorld.txt移动到IOTest目录下去；之后遍历IOTest这个目录下的文
     * 件
     */
    @Test
    public void test1() throws IOException {
        File file = new File("D:\\HelloWorld.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        System.out.println("HelloWorld.txt是否为文件：" + file.isFile());
        File file1 = new File("D:\\IOTest");
        file1.mkdir();
        file.renameTo(new File("D:\\IOTest\\HelloWorld.txt"));
        File file2 = new File("D:\\IOTest\\123.txt");
        file2.createNewFile();
        for (File file3 : file1.listFiles()) {
            System.out.println(file3.getName());
        }
    }

    /**
     * （二） 递归实现输入任意目录，列出文件以及文件夹，效果看图
     */
    @Test
    public void test2() throws Exception {
        getFileList(new File("D:\\home"));
        for (String str : fileList) {
            System.out.println("文件：" + str);
        }
        for (String str : dirList) {
            System.out.println("文件夹：" + str);
        }

    }

    public void getFileList(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (file.listFiles() == null) {
            return;
        }
        for (File file1 : file.listFiles()) {
            if (file1.isFile()) {
                fileList.add(file1.getName());
            } else if (file1.isDirectory()) {
                dirList.add(file1.getName());
            }
            getFileList(file1);
        }
    }

    /**
     * （三） 递归实现列出当前工程下所有.java文件
     */
    @Test
    public void test3() throws IOException {
        File file = new File("D:\\home\\groot-error.log.2018-06-02.log");
        String path = IoTest.class.getClass().getResource("/").getPath();
        System.out.println(path);
        System.out.println(file.getName().endsWith(".l"));
    }

    /**
     * （四）使用随机文件流类RandomAccessFile将一个文本文件倒置读出
     */
    @Test
    public void test4() {
        File file = new File("D:\\home\\groot-error.log.2018-06-02.log");
        File file2 = new File("D:\\home\\backup.log");
        RandomAccessFile accessFile = null;
        FileOutputStream out = null;
        try {
            accessFile = new RandomAccessFile(file, "r");
            out = new FileOutputStream(file2);
            long length = accessFile.length();
            while (length > 0) {
                length--;
                accessFile.seek(length);
                int val = accessFile.read();
                if (val >= 0 && val <= 255) {
                    out.write(val);
                } else {
                    length--;
                    accessFile.seek(length);
                    byte[] bytes = new byte[2];
                    accessFile.readFully(bytes);
                    out.write(bytes);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (accessFile != null) {
                try {
                    accessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
