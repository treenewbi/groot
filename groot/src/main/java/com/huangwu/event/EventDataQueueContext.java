package com.huangwu.event;

import com.huangwu.domain.EtcdModify;
import com.huangwu.domain.EtcdModify;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 构建一个事件数据队列上下文，用于模块间解耦
 *
 * @Package: com.huangwu.event
 * @Author: huangwu
 * @Date: 2018/5/20 9:12
 * @Description:
 * @LastModify:
 */
public class EventDataQueueContext {
    /**
     * etcd变更记录队列
     */
    private static ArrayBlockingQueue<EtcdModify> etcdModifiesQueue = new ArrayBlockingQueue<>(10000);

    public static ArrayBlockingQueue<EtcdModify> getEtcdModifiesQueue() {
        return etcdModifiesQueue;
    }

}
