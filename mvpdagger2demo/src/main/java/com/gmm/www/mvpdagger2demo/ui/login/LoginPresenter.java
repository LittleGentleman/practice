package com.gmm.www.mvpdagger2demo.ui.login;

import com.gmm.www.mvpdagger2demo.base.AbsPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */
public class LoginPresenter extends AbsPresenter<LoginContract.Model,LoginContract.View> implements LoginContract.Presenter {

    private Disposable disposable;

    /**
     * 这里由构造方法提供注入实例
     * @param model
     * @param view
     */
    @Inject //@Inject作用二: 用来标记构造函数，
    // Dagger2通过@Inject注解可以在需要这个类实例的时候来找到这个构造函数并把相关实例构造出来
    // ，以此来为被@Inject标记了的变量提供依赖
    public LoginPresenter(LoginContract.Model model, LoginContract.View view){
        this.mModel = model;
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void login(String phone, String password) {
        disposable = mModel.login(phone,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::loginSuccess,s -> System.out.println(s.getMessage()));
    }

    @Override
    public void start() {
        mView.setPresenter(this);
    }

    @Override
    public void onDestroy() {
        if (null != disposable && !disposable.isDisposed())
            disposable.dispose();

        disposable = null;
        mView = null;
    }
}
