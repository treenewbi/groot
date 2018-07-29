package com.huangwu.event.observer;

import com.huangwu.domain.EtcdModify;
import com.huangwu.event.EventDataQueueContext;
import com.huangwu.event.Observable;
import com.huangwu.event.Observer;
import com.huangwu.event.observable.EtcdObservable;
import com.huangwu.common.NamedThreadFactory;
import com.huangwu.domain.EtcdModify;
import com.huangwu.event.EventDataQueueContext;
import com.huangwu.event.Observable;
import com.huangwu.event.Observer;
import com.huangwu.event.observable.EtcdObservable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * etcd修改日志观察者
 *
 * @Package: com.huangwu.event.observer
 * @Author: huangwu
 * @Date: 2018/5/19 20:18
 * @Description:
 * @LastModify:
 */
public class EtcdModifyLogObserver implements Observer {

    private Logger logger = LoggerFactory.getLogger(EtcdModifyLogObserver.class);

    public EtcdModifyLogObserver(Observable o) {
        o.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof EtcdObservable) {
            EtcdObservable observable = (EtcdObservable) o;
            try {
                putQueue(observable.getEvenData());
            } catch (InterruptedException e) {
                logger.error("execute etcdModifiesQueue.put() is in trouble:", e);
            }
        }
    }

    private void putQueue(EtcdModify data) throws InterruptedException {
        EventDataQueueContext.getEtcdModifiesQueue().put(data);
    }
}
