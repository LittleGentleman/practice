package com.gmm.www.processdemo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.lang.ref.WeakReference;

/**
 * @author:gmm
 * @date:2020/4/21
 * @类说明:
 */
public class KeepManager {

    private static final KeepManager mInstance = new KeepManager();
    //广播
    private KeepReceiver mKeepReceiver;
    //弱引用
    private WeakReference<Activity> mKeepActivity;

    private KeepManager() {

    }

    public static KeepManager getInstance() {
        return mInstance;
    }

    /**
     * 注册 开屏 关屏 广播  （也可以在Manifest中注册广播）
     */
    public void registerKeep(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        mKeepReceiver = new KeepReceiver();
        context.registerReceiver(mKeepReceiver,filter);
    }

    /**
     * 注销  广播接收器
     */
    public void  unregisterKeep(Context context) {
        if (null != mKeepReceiver) {
            context.unregisterReceiver(mKeepReceiver);
        }
    }

    /**
     * 打开1像素Activity
     */
    public void startKeep(Context context) {
        Intent intent = new Intent(context,KeepActivity.class);
        //结合 taskAffinity 一起使用 在指定栈中创建这个activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 关闭1像素Activity
     */
    public void finishKeep() {
        if (null != mKeepActivity) {
            Activity activity = mKeepActivity.get();
            if (null != activity) {
                activity.finish();
            }
            mKeepActivity = null;
        }
    }

    /**
     * 设置若引用
     */
    public void setKeep(KeepActivity keep) {
        mKeepActivity = new WeakReference<Activity>(keep);
    }
}
