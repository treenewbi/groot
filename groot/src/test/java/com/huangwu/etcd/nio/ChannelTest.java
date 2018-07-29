package com.huangwu.etcd.nio;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Package: com.huangwu.etcd.nio
 * @Author: huangwu
 * @Date: 2018/7/18 15:04
 * @Description:
 * @LastModify:
 */
public class ChannelTest {

    @Test
    public void useNio() {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("F:\\niotest.txt", "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(24);
            int byteReader = channel.read(byteBuffer);
            while (byteReader != -1) {
                System.out.print("Read:" + byteReader);
                byteBuffer.flip();

                while (byteBuffer.hasRemaining()) {
                    System.err.print((char) byteBuffer.get());
                }
                byteBuffer.clear();
                byteReader = channel.read(byteBuffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testTransferFrom() throws Exception {
        RandomAccessFile fileFrom = new RandomAccessFile("F:\\niotest.txt", "rw");
        FileChannel fromChannel = fileFrom.getChannel();
        RandomAccessFile fileTo = new RandomAccessFile("F:\\to.txt", "rw");
        FileChannel toChannel = fileTo.getChannel();
        long position = 0;
        long count = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, count);
    }

    @Test
    public void testTransferTo() throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile("F:\\niotest.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile("F:\\to.txt", "rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        fromChannel.transferTo(position, count, toChannel);
    }
}
