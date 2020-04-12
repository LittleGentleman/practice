package com.gmm.www.mvpdemo03.ui.login;

import com.gmm.www.mvpdemo03.R;
import com.gmm.www.mvpdemo03.base.BaseActivity;
import com.gmm.www.mvpdemo03.utils.UIUtils;

/**
 * @author:gmm
 * @date:2020/3/14
 * @类说明:
 */
public class LoginActivity extends BaseActivity<LoginContract.Presenter> {
    @Override
    public int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (loginFragment == null)
            loginFragment = LoginFragment.newInstance();

        UIUtils.addFragmentToActivity(getSupportFragmentManager(),loginFragment,R.id.frame_layout);

        mPresenter = new LoginPresenter();
        ((LoginPresenter) mPresenter).setView(loginFragment);
        ((LoginPresenter) mPresenter).setModel(new LoginModel());
    }

    @Override
    public void initData() {

    }
}
