package com.gmm.rxjava.subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:gmm
 * @date:2020/2/15
 * @类说明: 具体被观察者角色
 */
public class WechatServer implements Observable {

    //观察者清单
    private List<Observer> list;

    private String message;

    public WechatServer() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(Observer observer) {
        list.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        list.remove(observer);
    }

    public void pushMessage(String msg) {
        this.message = msg;
        System.out.println("微信服务号更新消息了：" + message);
        notifyObservers();//通知所有观察者
    }

    @Override
    public void notifyObservers() {
        for (Observer observer :
                list) {
            observer.update(message);
        }
    }
}
