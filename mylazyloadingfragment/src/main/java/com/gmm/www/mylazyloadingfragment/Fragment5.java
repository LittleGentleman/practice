package com.gmm.www.mylazyloadingfragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * @author:gmm
 * @date:2020/4/14
 * @类说明:
 */
public class Fragment5 extends LazyFragment {
    private static final String TAG = "LazyFragment";
    public static Fragment newInstance() {
        Fragment5 fragment = new Fragment5();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_layout05;
    }

    @Override
    protected void initView(View rootView) {
        Log.e(TAG, "initView: Fragment5");
    }

    @Override
    protected void onFragmentFirstVisible() {

    }
}
