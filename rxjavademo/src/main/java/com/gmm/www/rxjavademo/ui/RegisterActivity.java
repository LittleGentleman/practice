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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RegisterActivity extends AppCompatActivity {


    private static final String TAG = "RegisterActivity";
    @BindView(R.id.btn)
    Button button;

    private static int SECOND = 20;
    private Observable<Boolean> verifyCodeObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        try {
            //把View封装成观察者observer，接收发射过来的数据
            RxTextView.text(button).accept("获取验证码");//Button 是TextView 的派生类
        } catch (Exception e) {
            e.printStackTrace();
        }

        //lambda
        verifyCodeObservable = RxView.clicks(button)//点击事件封装成observable，发射点击事件
                .throttleFirst(SECOND, TimeUnit.SECONDS)//防止20s内重复点击
                .subscribeOn(AndroidSchedulers.mainThread())//指定observable自身在哪个调度器上执行
                .map(o -> false)//发射数据 false
                .doOnNext(button::setEnabled);//接收数据

        verifyCodeObservable.subscribe(aBoolean -> {//接收点击事件
            Observable.interval(1, TimeUnit.SECONDS,AndroidSchedulers.mainThread())//发射间隔（每隔1s发射一次）
                    .take(SECOND)//发射的次数 （发射20次）
                    .subscribe(aLong -> {//
                        RxTextView.text(button).accept("剩余"+(SECOND-aLong)+"秒");
                            }
                            , Throwable::printStackTrace
                            , () -> {//全部发射完成之后  complete action
                                RxTextView.text(button).accept("获取验证码");
                                RxView.enabled(button).accept(true);
                            });
        });

        //java
//        verifyCodeObservable = RxView.clicks(button)// ①clicks返回的是Observable类型
//                .throttleFirst(SECOND,TimeUnit.SECONDS)
//                .subscribeOn(AndroidSchedulers.mainThread())//observable在主线程执行发射数据
//                .map(new Function<Object, Boolean>() {
//                    @Override
//                    public Boolean apply(Object o) throws Exception {//发射数据
//                        return false;
//                    }
//                })
//                .doOnNext(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {//接收数据
//                        button.setEnabled(aBoolean);
//                    }
//                });
//
//        verifyCodeObservable.subscribe(new Consumer<Boolean>() {//②subscribe返回的是Disposable类型，所里上下两部分不能写在一起 （如果不接收返回值，就可以写在一起）
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {//接收点击事件
//                Observable.interval(1,TimeUnit.SECONDS,AndroidSchedulers.mainThread())//发射间隔
//                        .take(SECOND)//发射次数
//                        .subscribe(new Consumer<Long>() {
//                            @Override
//                            public void accept(Long aLong) throws Exception {//aLong:发射数据的序号
//                                Log.d(TAG, "accept: " + aLong);
//                                RxTextView.text(button).accept("剩余" + (SECOND - (aLong+1)) + "秒");
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                throwable.printStackTrace();
//                            }
//                        }, new Action() {
//                            @Override
//                            public void run() throws Exception {
//                                RxTextView.text(button).accept("获取验证码");
//                                RxView.enabled(button).accept(true);
//                            }
//                        });
//            }
//        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verifyCodeObservable.unsubscribeOn(AndroidSchedulers.mainThread());//防止泄露
    }
}
