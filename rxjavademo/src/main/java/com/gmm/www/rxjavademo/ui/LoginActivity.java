package com.gmm.www.rxjavademo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.gmm.www.rxjavademo.R;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        //EditText 的输入文本改变监听 封装成 observable 被观察者，当输入框内容改变，就会发射EditText内的内容
        Observable<CharSequence> observablePhone = RxTextView.textChanges(etPhone);
        Observable<CharSequence> observablePwd = RxTextView.textChanges(etPwd);

        //将多个observable发射的数据组装起来然后再发射
//        Observable.combineLatest(observablePhone, observablePwd, new BiFunction<CharSequence, CharSequence, Object>() {
//            @Override
//            public Boolean apply(CharSequence phone, CharSequence pwd) throws Exception {//发射组装后的数据
//                return isPhoneValid(phone.toString()) && isPwdValid(pwd.toString());
//            }
//        }).subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {//接收发射过来的数据
//                btnLogin.setEnabled((Boolean) o);
//            }
//        });

        //lambda  将多个observable发射的数据组装起来然后再发射
        Observable.combineLatest(observablePhone,observablePwd,(phone,pwd)
                -> isPhoneValid(phone.toString()) && isPwdValid(pwd.toString()))//发射组装后的数据
                .subscribe(btnLogin::setEnabled);//接受发射过来的数据  enable：true可点击 false不可点击

        RxView.clicks(btnLogin)//发射点击事件
                .throttleFirst(2,TimeUnit.SECONDS)
                .subscribe(o -> Log.e(TAG, "登录"));


    }

    private boolean isPhoneValid(String phone) {
        return phone.length() == 11;
    }

    private boolean isPwdValid(String pwd) {
        return pwd.length() >= 6;
    }
}
