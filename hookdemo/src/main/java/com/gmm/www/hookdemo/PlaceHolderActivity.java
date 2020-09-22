package com.gmm.www.hookdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 占位Activity 用于偷梁换柱 欺骗Activity 达到插件化 启动没有注册的Activity
 */
public class PlaceHolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_holder);
    }
}
