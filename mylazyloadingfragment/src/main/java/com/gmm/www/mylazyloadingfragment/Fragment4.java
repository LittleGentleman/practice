package com.gmm.www.mylazyloadingfragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * @author:gmm
 * @date:2020/4/14
 * @类说明:
 */
public class Fragment4 extends LazyFragment {
    private static final String TAG = "LazyFragment";
    public static Fragment newInstance() {
        Fragment4 fragment = new Fragment4();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_layout04;
    }

    @Override
    protected void initView(View rootView) {
        Log.e(TAG, "initView: Fragment4");
    }

    @Override
    protected void onFragmentFirstVisible() {

    }
}
