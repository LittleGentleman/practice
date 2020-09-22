package com.gmm.www.processdemo.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author:gmm
 * @date:2020/4/21
 * @类说明:
 */
public class KeepActivity extends Activity {

    private static final String TAG = "KeepActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "启动一像素Activity保活");

        Window window = getWindow();
        //放在左上角
        window.setGravity(Gravity.START|Gravity.TOP);

        WindowManager.LayoutParams params = window.getAttributes();
        //设置宽高
        params.width = 1;
        params.height = 1;
        //设置起始坐标
        params.x = 0;
        params.y = 0;
        window.setAttributes(params);

        //KeepActivity 创建一个弱引用
        KeepManager.getInstance().setKeep(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "关闭一像素Activity" );
    }
}
