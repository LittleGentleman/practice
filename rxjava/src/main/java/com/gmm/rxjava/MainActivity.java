package com.gmm.rxjava;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "RXJAVA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.创建一个Observable，被观察者
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {//ObservableEmitter 发射器

                if (!emitter.isDisposed()){//判断是否需要中断
                    emitter.onNext("hello rxjava");
                    emitter.onNext("你好！！！！！");
                }

                emitter.onComplete();
            }
        });


        //2.创建一个Observer，观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG,"onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,"onNext s:" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"onComplete");
            }
        };

        //3.观察者 订阅 被观察者
        observable.subscribe(observer);
    }
}
