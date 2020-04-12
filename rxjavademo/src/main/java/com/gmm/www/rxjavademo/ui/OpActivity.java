package com.gmm.www.rxjavademo.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gmm.www.rxjavademo.R;

public class OpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op);

        //获取fragmentManager
        FragmentManager manager = getSupportFragmentManager();
        //开启一个事务
        FragmentTransaction transaction = manager.beginTransaction();
        //向FrameLayout容器添加Fragment，现在并未真正执行
        transaction.add(R.id.frameLayout,OpFragment.newInstance(),OpFragment.class.getName());
        //提交事务，真正执行添加操作
        transaction.commit();
    }
}
