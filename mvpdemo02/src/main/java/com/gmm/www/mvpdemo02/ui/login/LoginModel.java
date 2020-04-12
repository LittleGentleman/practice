package com.gmm.www.mvpdemo02.ui.login;

import com.gmm.www.mvpdemo02.network.api.WanAndroidApi;
import com.gmm.www.mvpdemo02.network.bean.BaseResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @author:gmm
 * @date:2020/3/12
 * @类说明:
 */
public class LoginModel implements LoginContract.Model {

    private WanAndroidApi wanAndroidApi;

    public void setWanAndroidApi(WanAndroidApi wanAndroidApi) {
        this.wanAndroidApi = wanAndroidApi;
    }

    @Override
    public Observable<String> login(String phone, String password) {
        return wanAndroidApi.login(phone,password)
                .map(baseResponse -> baseResponse.getData().toString())
                .toObservable();
//        return Observable.just("登录成功："+phone)
//                .delay(1000,TimeUnit.MILLISECONDS);
    }
}
