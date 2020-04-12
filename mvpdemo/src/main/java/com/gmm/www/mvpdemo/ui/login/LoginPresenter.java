package com.gmm.www.mvpdemo.ui.login;

import com.gmm.www.mvpdemo.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:gmm
 * @date:2020/3/10
 * @类说明:
 */
public class LoginPresenter implements LoginContact.Presenter {
    private LoginContact.View view;

    private Disposable disposable;

    LoginPresenter(LoginContact.View view){
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void login(String mobile, String password) {
        disposable = Observable.just("登录成功：" + mobile)
                .debounce(500,TimeUnit.MILLISECONDS)
                .delay(3000,TimeUnit.MILLISECONDS)//延迟发射
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::loginSuccess);
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
        view = null;
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
            disposable = null;
        }
    }
}
