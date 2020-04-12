package com.gmm.www.mvpdagger2demo.ui.register;

import com.gmm.www.mvpdagger2demo.base.AbsPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:presenter层负责逻辑功能代码、调用网络数据、本地数据封装层的编写
 */
public class RegisterPresenter extends AbsPresenter<RegisterContract.Model,RegisterContract.View> implements RegisterContract.Presenter {
    private Disposable disposable;

    @Inject
    public RegisterPresenter(RegisterContract.Model model, RegisterContract.View view){
        this.mModel = model;
        this.mView = view;
    }

    @Override
    public void register(String phone, String password, String repassword) {
        disposable = mModel.register(phone,password,repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::registerSuccess,s -> System.out.println(s.getMessage()));
    }

    @Override
    public void start() {
        this.mView.setPresenter(this);
    }

    @Override
    public void onDestroy() {
        if (null != disposable && !disposable.isDisposed())
            disposable.dispose();

        this.mView = null;
        this.disposable = null;
    }
}
