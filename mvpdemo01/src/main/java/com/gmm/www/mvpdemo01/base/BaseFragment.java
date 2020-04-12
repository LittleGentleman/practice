package com.gmm.www.mvpdemo01.base;

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
    private Unbinder mUnbinder;
    protected P mPresenter;
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(layoutId(),container,false);
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        initView();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();

        if (mPresenter != null)
            mPresenter.onDestroy();

        mUnbinder = null;
        mPresenter = null;
        mActivity = null;
        mRootView = null;
    }

    public abstract int layoutId();

    public abstract void initData();

    public abstract void initView();
}
