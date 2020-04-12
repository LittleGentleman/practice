package com.gmm.www.mvpdemo01.ui.login;

import android.nfc.Tag;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:gmm
 * @date:2020/3/11
 * @类说明:
 */
public class LoginPresenter implements LoginContact.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    private LoginContact.View mView;
    private Disposable disposable;

    LoginPresenter(LoginContact.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void login(String mobile, String password) {
        disposable = Observable.just("登录成功："+ mobile)
                .debounce(500,TimeUnit.MILLISECONDS)
                .delay(2000,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::loginSuccess);
    }

    @Override
    public void start() {
        Log.i(TAG, "start: " + "初始化数据");
    }

    @Override
    public void onDestroy() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();

        disposable = null;
        mView = null;
    }
}
