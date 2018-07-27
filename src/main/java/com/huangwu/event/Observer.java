package com.huangwu.event;

/**
 * 观察者接口
 *
 * @Package: huangwu.util
 * @Author: huangwu
 * @Date: 2018/5/14 13:36
 * @Description:
 * @LastModify:
 */
public interface Observer {
    /**
     * 当被观察者发生变化时，此方法被调用
     *
     * @param o 被观察者
     * @param arg
     */
    void update(Observable o, Object arg);
}
