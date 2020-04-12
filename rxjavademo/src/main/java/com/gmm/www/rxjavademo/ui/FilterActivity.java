package com.gmm.www.rxjavademo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.gmm.www.rxjavademo.R;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class FilterActivity extends AppCompatActivity {
    private static final String TAG = "FilterActivity";
    @BindView(R.id.edit_text)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        RxTextView.textChanges(editText)//内容改动就发射数据
                //防抖动  每隔某时间再发射一次数据  与throttleFirst不同，
                // throttleFirst是某时间内产生的数据不会发射，debounce是某时间内产生的数据会在时间到了之后发射最后一条数据
                .debounce(1,TimeUnit.SECONDS)
                //跳过第一次请求，第一次不发射  因为初始输入框的空字符状态
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        Log.e(TAG, "接收到的字符: " + charSequence);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });

        //lambda
//        RxTextView.textChanges(editText)
//                .debounce(1,TimeUnit.SECONDS)
//                .skip(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(charSequence -> Log.e(TAG, "接收到的字符: " + charSequence)
//                         ,e -> Log.e(TAG, "onError: " + e.getMessage())
//                        ,() -> Log.e(TAG, "onComplete: "));

    }
}
