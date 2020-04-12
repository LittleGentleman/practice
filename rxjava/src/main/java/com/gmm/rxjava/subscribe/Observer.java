package com.gmm.rxjava.subscribe;

/**
 * @author:gmm
 * @date:2020/2/15
 * @类说明:抽象观察者角色
 *定义update方法，当被观察者调用notifyObservers方法时，回调观察者的update方法
 */
public interface Observer {
    void update(Object arg0);
}
