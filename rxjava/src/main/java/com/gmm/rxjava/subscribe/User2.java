package com.gmm.rxjava.subscribe;

import java.util.Observable;
import java.util.Observer;

/**
 * @author:gmm
 * @date:2020/2/19
 * @类说明:具体的观察者角色 使用jdk中的Observer
 */
public class User2 implements Observer {

    private String name;
    private String message;

    public User2(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.message = (String) arg;
        print();
    }

    private void print() {
        System.out.println(name + "收到了推送消息：" + message);
    }
}
