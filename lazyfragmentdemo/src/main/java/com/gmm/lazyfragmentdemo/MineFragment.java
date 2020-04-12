package com.gmm.lazyfragmentdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MineFragment extends LazyFragment {
    private String TAG = "MINE";
    private Timer timer;
    private TimerTask task;
    private TextView textView;
    @Override
    public int getRes() {
        return R.layout.fragemnt_mine;
    }

    @Override
    public void initView(View rootView) {
        textView = rootView.findViewById(R.id.text);
    }

    @Override
    public String getT() {
        return TAG;
    }

    public void onFragmentLoad() {
        Log.e(TAG,"数据开始加载");
        if (task == null && timer == null) {
            task = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                }
            };
            timer = new Timer();
            timer.schedule(task,2000);
        }

    }

    @Override
    public void onFragmentLoadStop() {
        Log.e(TAG,"数据停止加载");
        if (task != null){
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        textView.setText("停止加载我的");

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    textView.setText("加载后的我的");
                    break;
            }
        }
    };
}
