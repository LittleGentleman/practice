package com.gmm.www.mvpdemo01.base;

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

    private Application mApplication;
    private Unbinder mUnBinder;
    protected P mPresenter;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplication = getApplication();
        mContext = this;

        setContentView(layoutId());

        mUnBinder = ButterKnife.bind(this);

        initData();

    }

    public abstract int layoutId();

    public abstract void initData();

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
        mApplication = null;
    }
}
