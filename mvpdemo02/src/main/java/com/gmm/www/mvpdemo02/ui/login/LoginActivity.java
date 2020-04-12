package com.gmm.www.mvpdemo02.ui.login;

import com.gmm.www.mvpdemo02.R;
import com.gmm.www.mvpdemo02.base.BaseActivity;
import com.gmm.www.mvpdemo02.utils.UIUtils;

/**
 * @author:gmm
 * @date:2020/3/12
 * @类说明:
 */
public class LoginActivity extends BaseActivity<LoginContract.Presenter>{
    @Override
    public int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            UIUtils.addFragmentToActivity(getSupportFragmentManager(),loginFragment,R.id.frame_layout);
        }

        //初始化Presenter
        mPresenter = new LoginPresenter();
        //外部注入
        ((LoginPresenter) mPresenter).setView(loginFragment);
        ((LoginPresenter) mPresenter).setModel(new LoginModel());
    }

    @Override
    public void initData() {

    }

}
