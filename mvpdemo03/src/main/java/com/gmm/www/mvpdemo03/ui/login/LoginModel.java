package com.gmm.www.mvpdemo03.ui.login;

import com.gmm.www.mvpdemo03.network.api.WanAndroidApi;

import io.reactivex.Observable;

/**
 * @author:gmm
 * @date:2020/3/14
 * @类说明:
 */
public class LoginModel implements LoginContract.Model {

    WanAndroidApi wanAndroidApi;

    public void setWanAndroidApi(WanAndroidApi wanAndroidApi) {
        this.wanAndroidApi = wanAndroidApi;
    }

    @Override
    public Observable<String> login(String phone, String password) {

        return wanAndroidApi.login(phone,password)
                .map(baseResponse -> baseResponse.getData().toString());

//        return Observable.just("登录成功：" + phone)
//                .delay(1000,TimeUnit.MILLISECONDS);
    }
}
