package com.gmm.www.mvpdagger2demo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gmm.www.mvpdagger2demo.App;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    protected App mApp;
    protected Context mContext;
    protected Unbinder mUnbinder;
    @Inject// @Inject作用一: 用来标记需要依赖的变量
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (App) getApplication();
        mContext = this;

        setContentView(layoutId());
        mUnbinder = ButterKnife.bind(this);

        initView();
        initData();
    }

    protected abstract int layoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mUnbinder && Unbinder.EMPTY != mUnbinder)
            mUnbinder.unbind();

        if (null != mPresenter)
            mPresenter.onDestroy();

        mUnbinder = null;
        mPresenter = null;
        mApp = null;
        mContext = null;
    }
}
