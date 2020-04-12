package com.gmm.www.mvpdemo03.base;

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
import retrofit2.http.PUT;

/**
 * @author:gmm
 * @date:2020/3/14
 * @类说明:
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
    protected Activity mActivity;
    protected Unbinder mUnbidner;
    protected P mPresenter;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(layoutId(),container,false);

        mUnbidner = ButterKnife.bind(this,mRootView);
        initView();
        initData();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mPresenter)
            mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Unbinder.EMPTY != mUnbidner)
            mUnbidner.unbind();
    }

    public abstract int layoutId();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter)
            mPresenter.onDestroy();

        mUnbidner = null;
        mPresenter = null;
        mRootView = null;
        mActivity = null;
    }
}
