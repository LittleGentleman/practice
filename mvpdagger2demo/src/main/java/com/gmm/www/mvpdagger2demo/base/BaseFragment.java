package com.gmm.www.mvpdagger2demo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    protected Activity mActivity;
    protected Unbinder mUnbinder;
    protected P mPresenter;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(layoutId(),container,false);
        mUnbinder = ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        initView();
        initData();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mPresenter)
            mPresenter.start();
    }

    protected abstract int layoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mUnbinder && Unbinder.EMPTY != mUnbinder)
            mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter)
            mPresenter.onDestroy();

        mUnbinder = null;
        mPresenter = null;
        mActivity = null;
        mRootView = null;
    }
}
