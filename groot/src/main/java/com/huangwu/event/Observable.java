package com.huangwu.event;

import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 定义一个被观察者基类
 *
 * @Package: huangwu.util
 * @Author: huangwu
 * @Date: 2018/5/14 13:34
 * @Description:
 * @LastModify:
 */
public class Observable {
    /**
     * 全局锁，默认非公平
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 标记，当值为true时才调用Observer的update方法
     */
    private boolean changed = false;

    /**
     * 观察者集合
     */
    private Vector<Observer> observers;

    public Observable() {
        this.observers = new Vector<>();
    }

    /**
     * 添加一个观察者
     *
     * @param o 观察者
     */
    public void addObserver(Observer o) {
        if (o == null) {
            throw new NullPointerException();
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (!observers.contains(o)) {
                observers.add(o);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 删除一个观察者
     *
     * @param o
     */
    public void deleteObserver(Observer o) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            observers.removeElement(o);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 唤醒所有观察者
     */
    public void notifyObservers() {
        notifyObservers(null);
    }

    /**
     * 唤醒所有观察者
     *
     * @param arg
     */
    public void notifyObservers(Object arg) {
        Object[] arrLocal;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (!changed) {
                return;
            }
            arrLocal = observers.toArray();
            clearChanged();
        } finally {
            lock.unlock();
        }
        for (int i = 0; i < arrLocal.length; i++) {
            ((Observer) arrLocal[i]).update(this, arg);
        }
    }

    /**
     * 清除标志
     */
    protected void clearChanged() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            changed = false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 移除所有的观察者
     */
    public void deleteObservers() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            observers.removeAllElements();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 设置标记
     */
    protected void setChanged() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            this.changed = true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 是否标记
     *
     * @return
     */
    public boolean hasChanged() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return changed;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获得当前被观察者的观察者数量
     *
     * @return 被观察者数量
     */
    public int countObservers() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return observers.size();
        } finally {
            lock.unlock();
        }
    }

}
