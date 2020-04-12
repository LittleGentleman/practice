package com.gmm.www.mvpdemo01.ui.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gmm.www.mvpdemo01.R;
import com.gmm.www.mvpdemo01.UIUtils;
import com.gmm.www.mvpdemo01.base.BaseActivity;
import com.gmm.www.mvpdemo01.base.BaseView;

public class LoginActivity extends BaseActivity<LoginContact.Presenter>{


    @Override
    public int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameLayout);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            UIUtils.addFragmentToActivity(getSupportFragmentManager(),loginFragment,R.id.contentFrameLayout);//FrameLayout中添加Fragment
        }

        mPresenter = new LoginPresenter(loginFragment);
    }
}
