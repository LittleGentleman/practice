package com.gmm.www.mvpdagger2demo.ui.login;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmm.www.mvpdagger2demo.R;
import com.gmm.www.mvpdagger2demo.base.BaseFragment;
import com.gmm.www.mvpdagger2demo.ui.main.MainActivity;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */
public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {

        Observable<CharSequence> observableName = RxTextView.textChanges(et_username);
        Observable<CharSequence> observablePassword = RxTextView.textChanges(et_password);

        Observable.combineLatest(observableName
                ,observablePassword
                ,(phone,password) -> isPhoneValid(phone.toString())&& isPasswordValid(password.toString()))
                .subscribe(btn_login::setEnabled);

        RxView.clicks(btn_login)
                .throttleFirst(1,TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    mPresenter.login(et_username.getText().toString(),et_password.getText().toString());
                });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void loginSuccess(LoginContract.Model result) {
        if (result.isSuccess())
            startActivity(new Intent(mActivity,MainActivity.class));
        else
            Toast.makeText(mActivity, result.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
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
