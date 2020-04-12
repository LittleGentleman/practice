package com.gmm.www.rxjavademo.rx;

import android.view.View;

import io.reactivex.Observable;

/**
 * @author:gmm
 * @date:2020/3/3
 * @类说明:
 */
public class MyRxView {

    //把按钮点击事件 转化成 Observable<>形式
    public static Observable<Object> clicks(View view) {
        return new ViewClickObservable(view);
    }
}
