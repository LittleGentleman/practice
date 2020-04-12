package com.gmm.www.mvpdemo01.ui.login;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmm.www.mvpdemo01.R;
import com.gmm.www.mvpdemo01.base.BaseFragment;
import com.gmm.www.mvpdemo01.base.BasePresenter;
import com.gmm.www.mvpdemo01.base.BaseView;
import com.gmm.www.mvpdemo01.ui.MainActivity;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * @author:gmm
 * @date:2020/3/11
 * @类说明:
 */
public class LoginFragment extends BaseFragment<LoginContact.Presenter> implements LoginContact.View {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;

    protected static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        Observable<CharSequence> phoneObservable = RxTextView.textChanges(et_username);
        Observable<CharSequence> passwordObservable = RxTextView.textChanges(et_password);

        Observable.combineLatest(phoneObservable,passwordObservable,(phone,password) -> {
                   return isPhoneValid(phone.toString()) && isPasswordValid(password.toString());
                }).subscribe(btn_login::setEnabled);

        RxView.clicks(btn_login)
                .throttleFirst(800,TimeUnit.MILLISECONDS)
                .subscribe(o -> mPresenter.login(et_username.getText().toString(),et_password.getText().toString()));
    }


    @Override
    public void loginSuccess(String result) {
        Toast.makeText(mActivity, result, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(mActivity,MainActivity.class));
    }

    @Override
    public void setPresenter(LoginContact.Presenter presenter) {
        if (mPresenter == null)
            this.mPresenter = presenter;
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() == 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length()>=6;
    }
}
