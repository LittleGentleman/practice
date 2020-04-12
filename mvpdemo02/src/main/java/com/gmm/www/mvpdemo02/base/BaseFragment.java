package com.gmm.www.mvpdemo02.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author:gmm
 * @date:2020/3/11
 * @类说明:
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
    protected BaseActivity mActivity;
    protected Unbinder mUnbinder;
    protected P mPresenter;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(layoutId(),container,false);
        //绑定到butterknife
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        initView();
        initData();
    }

    public abstract int layoutId();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();

        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();

        mActivity = null;
        mRootView = null;
        mPresenter = null;
        mUnbinder = null;
    }
}
