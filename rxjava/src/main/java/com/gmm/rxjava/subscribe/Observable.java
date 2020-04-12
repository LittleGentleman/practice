package com.gmm.rxjava.subscribe;

/**
 * @author:gmm
 * @date:2020/2/15
 * @类说明: 抽象被观察者角色
 *
 * 声明 添加观察者、删除观察者、通知观察者的方法
 */
public interface Observable {

    void add(Observer observer);
    void remove(Observer observer);
    void notifyObservers();
}
