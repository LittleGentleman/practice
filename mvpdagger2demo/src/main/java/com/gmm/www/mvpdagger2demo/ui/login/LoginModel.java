package com.gmm.www.mvpdagger2demo.ui.login;

import com.gmm.www.mvpdagger2demo.network.api.WanAndroidApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */
public class LoginModel implements LoginContract.Model {

    private String msg;
    private String usr;
    private boolean isSuccess;

    @Inject
    public LoginModel() {
    }

    public LoginModel(String msg, String usr, boolean isSuccess) {
        this.msg = msg;
        this.usr = usr;
        this.isSuccess = isSuccess;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getUser() {
        return usr;
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Inject
    WanAndroidApi wanAndroidApi;

    @Override
    public Observable<LoginContract.Model> login(String phone, String password) {

        return wanAndroidApi.login(phone,password)
                .map(baseResponse -> (LoginContract.Model)new LoginModel(baseResponse.getErrorMsg(),phone,baseResponse.getErrorCode()==0?true:false))
                ;
    }
}
