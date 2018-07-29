package com.huangwu.etcd.other;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * 实现一个LRU算法
 *
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/24 14:41
 * @Description:
 * @LastModify:
 */
public class MyLru extends LinkedHashMap {
    private int size;

    public MyLru(int size) {
        super(16, 0.75f, true);
        this.size = size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        if (size() > size)
            return true;
        return false;
    }

    public static void main(String[] args) {
        MyLru lru = new MyLru(10);
        for (int i = 0; i < 10; i++) {
            lru.put(i,i);
        }
//        System.out.println(lru);
        for (int i = 10; i < 14; i++) {
            int index = new Random().nextInt(9);
            System.out.println(lru.get(index));
            lru.put(i,i);
            System.out.println("lru:" + lru);
        }
    }
}
