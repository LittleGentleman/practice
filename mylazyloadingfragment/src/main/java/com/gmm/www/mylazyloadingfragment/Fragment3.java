package com.gmm.www.mylazyloadingfragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * @author:gmm
 * @date:2020/4/14
 * @类说明:
 */
public class Fragment3 extends LazyFragment {
    private static final String TAG = "LazyFragment";

    public static Fragment newInstance() {
        Fragment3 fragment = new Fragment3();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_layout03;
    }

    @Override
    protected void initView(View rootView) {
        Log.e(TAG, "initView: Fragment3");
    }

    @Override
    protected void onFragmentFirstVisible() {

    }
}
