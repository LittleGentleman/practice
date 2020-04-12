package com.gmm.rxjava.subscribe;

import java.util.Observable;

/**
 * @author:gmm
 * @date:2020/2/19
 * @类说明: 具体的被观察角色 使用jdk提供的被观察者类 Observable
 */
public class WechatServer2 extends Observable {
    private String message;

    public void pushMessage(String msg) {
        this.message = msg;
        System.out.println("微信服务号更新消息了：" + message);
        setChanged();//修改Observable的属性change为true，否则不会通知观察者
        notifyObservers(message);
    }
}
