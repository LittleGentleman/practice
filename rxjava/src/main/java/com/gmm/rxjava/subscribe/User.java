package com.gmm.rxjava.subscribe;

import android.util.Log;

/**
 * @author:gmm
 * @date:2020/2/19
 * @类说明:具体的观察者角色
 */
public class User implements Observer{
    private String name;
    private String message;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(Object arg0) {
        this.message = (String) arg0;
        print();
    }

    private void print() {
        System.out.println(name + "收到了推送消息：" + message);
    }
}
