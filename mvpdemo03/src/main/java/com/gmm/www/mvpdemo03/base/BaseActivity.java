package com.gmm.www.mvpdemo03.base;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author:gmm
 * @date:2020/3/14
 * @类说明:
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected Context mContext;
    protected Application mApp;
    protected Unbinder mUnbinder;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mApp = getApplication();

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
        if (Unbinder.EMPTY != mUnbinder)
            mUnbinder.unbind();

        if (null != mPresenter)
            mPresenter.onDestroy();

        mUnbinder = null;
        mPresenter = null;
        mContext = null;
        mApp = null;
    }
}
