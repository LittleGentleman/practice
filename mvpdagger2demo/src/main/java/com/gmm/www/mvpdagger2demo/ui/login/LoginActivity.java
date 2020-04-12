package com.gmm.www.mvpdagger2demo.ui.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gmm.www.mvpdagger2demo.R;
import com.gmm.www.mvpdagger2demo.base.BaseActivity;
import com.gmm.www.mvpdagger2demo.ui.UIUtils;
import com.gmm.www.mvpdagger2demo.ui.login.di.DaggerLoginComponent;
import com.gmm.www.mvpdagger2demo.ui.login.di.LoginModule;

public class LoginActivity extends BaseActivity<LoginContract.Presenter> {


    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameLayout);
        if (null == loginFragment)
            loginFragment = LoginFragment.newInstance();

        UIUtils.addFragmentToActivity(getSupportFragmentManager(),loginFragment,R.id.contentFrameLayout);

        DaggerLoginComponent.builder()
                .appComponent(mApp.getAppComponent())
                .loginModule(new LoginModule(loginFragment))
                .build()
                .inject(this);


    }

    @Override
    protected void initData() {

    }
}
