package com.huangwu.event.observable;

import com.huangwu.event.Observable;
import com.huangwu.domain.EtcdModify;
import com.huangwu.event.Observable;

/**
 * etcd路径变动被观察者对象
 *
 * @Package: com.huangwu.event.observable
 * @Author: huangwu
 * @Date: 2018/5/19 20:08
 * @Description:
 * @LastModify:
 */
public class EtcdObservable extends Observable {
    /**
     * 被观察者名称
     */
    private String name;

    /**
     * 事件数据
     */
    private EtcdModify evenData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EtcdModify getEvenData() {
        return evenData;
    }

    public void setEvenData(EtcdModify evenData) {
        this.evenData = evenData;
    }

    /**
     * 向观察者推送事件数据
     *
     * @param evenData 事件数据
     */
    public void pushEventDataToObservers(EtcdModify evenData) {
        this.evenData = evenData;
        setChanged();
        notifyObservers();
    }

    public EtcdObservable(String name) {
        this.name = name;
    }
}
