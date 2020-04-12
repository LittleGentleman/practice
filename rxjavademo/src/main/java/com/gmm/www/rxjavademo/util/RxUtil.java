package com.gmm.www.rxjavademo.util;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:gmm
 * @date:2020/2/24
 * @类说明:
 */
public class RxUtil {

    public static <T> ObservableTransformer<T,T> io_main() {
        return new ObservableTransformer<T, T>() {//将源ObservableSource整体转换
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {//upstream:上游
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
