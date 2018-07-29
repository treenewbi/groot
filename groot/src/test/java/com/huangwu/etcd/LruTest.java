package com.huangwu.etcd;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Package: com.huangwu.etcd
 * @Author: huangwu
 * @Date: 2018/6/19 10:28
 * @Description:
 * @LastModify:
 */
public class LruTest extends LinkedHashMap {

    private final int CACHE_SIZE = 0;

    public LruTest(int cacheSize) {
        super(cacheSize, 0.75f, true);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > CACHE_SIZE;
    }
}
