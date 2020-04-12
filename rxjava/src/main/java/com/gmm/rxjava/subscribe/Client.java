package com.gmm.rxjava.subscribe;

import java.util.Observable;
import java.util.Observer;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;

/**
 * @author:gmm
 * @date:2020/2/19
 * @类说明:
 */
public class Client {

    public static void main(String[] args) {
        /**
        //创建一个被观察者：微信服务号
        Observable observable = new WechatServer();//面向接口编程
        //创建观察者: 用户
        Observer observer1 = new User("James");//面向接口编程
        Observer observer2 = new User("Jone");//面向接口编程
        Observer observer3 = new User("Linda");//面向接口编程
        //订阅
        observable.add(observer1);
        observable.add(observer2);
        observable.add(observer3);
        //被观察者（微信服务号）推送消息
        ((WechatServer) observable).pushMessage("德玛西亚！！！");
         */

        //创建一个被观察者：微信服务号
        Observable observable = new WechatServer2();
        //创建观察者: 用户
        Observer observer1 = new User2("James");
        Observer observer2 = new User2("Jone");
        Observer observer3 = new User2("Linda");
        //订阅
        observable.addObserver(observer1);
        observable.addObserver(observer2);
        observable.addObserver(observer3);
        //推送消息
        ((WechatServer2) observable).pushMessage("一点寒芒先到，随后枪出如龙!");


    }
}
