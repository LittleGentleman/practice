package com.gmm.www.mvpdemo02.ui.login;

import com.gmm.www.mvpdemo02.base.AbsPresenter;
import com.gmm.www.mvpdemo02.network.HttpUtils;
import com.gmm.www.mvpdemo02.network.api.WanAndroidApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:gmm
 * @date:2020/3/12
 * @类说明:
 */
public class LoginPresenter extends AbsPresenter<LoginContract.Model,LoginContract.View> implements LoginContract.Presenter {
    private Disposable disposable;

    @Override
    public void setModel(LoginContract.Model mModel) {
        this.mModel = mModel;
        ((LoginModel)mModel).setWanAndroidApi(HttpUtils.getOnlineCookieRetrofit().create(WanAndroidApi.class));
    }

    @Override
    public void setView(LoginContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void login(String phone, String password) {
        disposable =  mModel.login(phone,password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(mView::loginSuccess);
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
        if (!disposable.isDisposed())
            disposable.dispose();

        disposable = null;
        mView = null;
        mModel = null;

    }
}
