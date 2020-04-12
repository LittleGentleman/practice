package com.gmm.www.mvpdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author:gmm
 * @date:2020/3/10
 * @类说明:
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected Context mContext;
    private Unbinder mUnBinder;
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(layoutId());
        mUnBinder = ButterKnife.bind(this);
        initData();
    }

    protected abstract int layoutId();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != Unbinder.EMPTY)
            mUnBinder.unbind();

        if (mPresenter != null)
            mPresenter.onDestroy();

        mUnBinder = null;
        mPresenter = null;
        mContext = null;

    }
}
