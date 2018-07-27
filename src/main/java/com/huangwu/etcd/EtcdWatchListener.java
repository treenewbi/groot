package com.huangwu.etcd;

public interface EtcdWatchListener {
    public void onWatch(EtcdActionResponse action);
}
