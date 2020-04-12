package com.gmm.www.mvpdemo.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmm.www.mvpdemo.R;
import com.gmm.www.mvpdemo.base.BaseActivity;
import com.gmm.www.mvpdemo.ui.MainActivity;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity<LoginContact.Presenter> implements LoginContact.View {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        new LoginPresenter(this).start();

        Observable<CharSequence> phoneObservable = RxTextView.textChanges(et_username);
        Observable<CharSequence> passwordObservable = RxTextView.textChanges(et_password);

        Observable.combineLatest(phoneObservable,passwordObservable,(phone,password)
            -> isPhoneValid(phone.toString()) && isPasswordValid(password.toString()))
                .subscribe(btn_login::setEnabled);

        RxView.clicks(btn_login)
                .throttleFirst(800,TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    mPresenter.login(et_username.getText().toString(),et_password.getText().toString());
                });

//        Observable.combineLatest(phoneObservable, passwordObservable, new BiFunction<CharSequence, CharSequence, Object>() {
//            @Override
//            public Boolean apply(CharSequence phone, CharSequence password) throws Exception {
//                return isPhoneValid(phone.toString()) && isPasswordValid(password.toString());
//            }
//        }).subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                btn_login.setEnabled((Boolean) o);
//            }
//        });

//        RxView.clicks(btn_login)
//                .throttleFirst(800,TimeUnit.MILLISECONDS)
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//
//                    }
//                });
    }

    @Override
    public void loginSuccess(String result) {
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(mContext,MainActivity.class));
    }

    @Override
    public void setPresenter(LoginContact.Presenter presenter) {
        if (mPresenter == null)
            mPresenter = presenter;
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() == 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
}
