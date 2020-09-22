package com.gmm.www.processdemo.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author:gmm
 * @date:2020/4/21
 * @类说明:  广播接收器 接收系统的开屏 和 关屏 广播
 * 代码注册了广播接收器 可以不用在Manifest中注册
 */
public class KeepReceiver extends BroadcastReceiver {
    private static final String TAG = "KeepReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.e(TAG, "onReceive: " + action);

        if (TextUtils.equals(action,Intent.ACTION_SCREEN_ON)) {
            //打开屏幕时 关闭1像素Activity
            KeepManager.getInstance().finishKeep();
        } else if (TextUtils.equals(action,Intent.ACTION_SCREEN_OFF)) {
            //关闭屏幕时 打开1像素Activity
            KeepManager.getInstance().startKeep(context);
        }
    }
}
