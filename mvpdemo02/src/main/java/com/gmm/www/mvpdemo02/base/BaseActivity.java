package com.gmm.www.mvpdemo02.base;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author:gmm
 * @date:2020/3/11
 * @类说明:
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    protected Application mApplication;
    protected Context mContext;
    protected P mPresenter;
    protected Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplication = getApplication();
        mContext = this;

        setContentView(layoutId());
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    public abstract int layoutId();

    public abstract void initView();

    public abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();

        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();

        mPresenter = null;
        mUnbinder = null;
        mApplication = null;
        mContext = null;
    }
}
