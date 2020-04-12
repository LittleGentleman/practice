package com.gmm.www.animationdemo02;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;


public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 设置布局文件
     */
    protected abstract void setContentView();

    /**
     * 初始化布局文件中的控件
     */
    protected abstract void initView();

    /**
     * 设置控件监听
     */
    protected abstract void initListener();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
