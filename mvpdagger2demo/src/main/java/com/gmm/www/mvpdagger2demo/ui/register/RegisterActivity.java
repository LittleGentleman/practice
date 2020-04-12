package com.gmm.www.mvpdagger2demo.ui.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmm.www.mvpdagger2demo.R;
import com.gmm.www.mvpdagger2demo.base.BaseActivity;
import com.gmm.www.mvpdagger2demo.ui.login.LoginActivity;
import com.gmm.www.mvpdagger2demo.ui.register.di.DaggerRegisterComponent;
import com.gmm.www.mvpdagger2demo.ui.register.di.RegisterModule;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View {
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_re_password)
    EditText et_re_password;
    @BindView(R.id.btn_register)
    Button btn_register;

    private static int SECOND = 20;
    private Observable<Boolean> registerObservable;

    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        DaggerRegisterComponent.builder()
                .appComponent(mApp.getAppComponent())
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);

        registerObservable = RxView.clicks(btn_register)
                .throttleFirst(SECOND,TimeUnit.SECONDS)//防止20秒内连续点击,或者只使用doOnNext部分
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(o -> false)
                .doOnNext(btn_register::setEnabled);
        registerObservable.subscribe(s ->
            Observable.interval(1,TimeUnit.SECONDS,AndroidSchedulers.mainThread())
                .take(SECOND)
                .subscribe(aLong -> RxTextView.text(btn_register).accept("剩余" + (SECOND-aLong) +"秒")
                ,Throwable::printStackTrace
                ,()->{
                    RxTextView.text(btn_register).accept("注 册");
                    RxView.enabled(btn_register).accept(true);
                        }));

        Observable<CharSequence> observableName = RxTextView.textChanges(et_username);
        Observable<CharSequence> observablePassword = RxTextView.textChanges(et_password);
        Observable<CharSequence> observableRePassword = RxTextView.textChanges(et_re_password);

        Observable.combineLatest(observableName
                ,observablePassword
                ,observableRePassword
                ,(usr,pwd,rePwd) -> registerValid(usr.toString(),pwd.toString(),rePwd.toString()))
                .subscribe(btn_register::setEnabled);

        RxView.clicks(btn_register)
                .throttleFirst(1,TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> mPresenter.register(et_username.getText().toString()
                ,et_password.getText().toString()
                ,et_re_password.getText().toString()));



    }

    @Override
    protected void initData() {

    }

    @Override
    public void registerSuccess(RegisterContract.Model result) {
        if (result.isSuccess())
            startActivity(new Intent(this,LoginActivity.class));
        else
            Toast.makeText(mContext, result.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {

    }

    private boolean registerValid(String usr,String pwd,String rePwd) {
        return isUsrValid(usr) && isPasswordValid(pwd,rePwd);
    }

    private boolean isUsrValid(String usr){
        return usr.length() == 11;
    }

    private boolean isPasswordValid(String pwd,String rePwd) {
        return pwd.length()>=6 && TextUtils.equals(pwd,rePwd);
    }
}
