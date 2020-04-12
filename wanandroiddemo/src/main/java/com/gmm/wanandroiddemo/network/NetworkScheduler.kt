package com.gmm.wanandroiddemo.network

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object NetworkScheduler {//静态类  线程切换
    fun <T> compose():ObservableTransformer<T,T>{

        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}