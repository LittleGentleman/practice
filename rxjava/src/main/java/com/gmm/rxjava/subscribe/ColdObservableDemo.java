package com.gmm.rxjava.subscribe;

import android.nfc.Tag;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:gmm
 * @date:2020/2/19
 * @类说明:冷： 观察者订阅了，才会开始执行发射数据流的代码。Observable 和 Observer 是一对一的关系
 * 对 Cold Observable 而言，有多个Observer的时候，它们各自的时间是独立的。
 * 事件类型             作用
 * onNext()             观察者会回调它的onNext()方法
 * onError()            onError事件发送之后，其他事件不会继续发送
 * onComplete()         onComplete事件发送之后，其他事件不会继续发送
 */
public class ColdObservableDemo {
    
    public static void main(String[] args) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                Observable.interval(10,TimeUnit.MILLISECONDS,
                        Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe();
            }
        });
    }
}
