package com.gmm.www.rxjavademo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.gmm.www.rxjavademo.R;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ButtonClicksActivity extends AppCompatActivity {

    private static final String TAG = "ButtonClicksActivity";
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_clicks);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        try {
            RxTextView.text(btn).accept("猛击按钮");//这是按钮文本
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 按钮点击防抖
         * observeOn：指定一个观察者在哪个调度器上观察这个Observable
         * subscribeOn：指定Observable自身在哪个调度器上执行
         */
        RxView.clicks(btn)
                //某时间内不重复发射数据
                .throttleFirst(1,TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> Log.e(TAG, "点击按钮 "));
    }


}
