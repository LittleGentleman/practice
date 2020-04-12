package com.gmm.www.mvpdagger2demo.ui.register;

import com.gmm.www.mvpdagger2demo.network.api.WanAndroidApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:Model层负责数据的访问操作，同时开放接口提供给presenter层进行调用
 */

public class RegisterModel implements RegisterContract.Model {
    //构造器注入  当有依赖需求方需要RegisterModel实例是，dagger会自动找到@Inject注解的RegisterModel进行实例
    @Inject
    public RegisterModel() {

    }

    //这里RegisterModel依赖了WanAndroidApi，有了@Inject标注后，
    // dagger会优先找有没有@Inject标注的WanAndroidApi的构造器，如果没有，
    // 会再找有没有@Module和@Provide所标注的方式提供了这个WanAndroidApi的实例，
    //这里没有构造器注入，但是有ApiServiceModule通过@Module和@Provide的方式,
    //提供了WanAndroidApi的实例
    @Inject
    WanAndroidApi wanAndroidApi;

    private String user;
    private String msg;
    private boolean isSuccess;

    public RegisterModel(String user, String msg, boolean isSuccess) {
        this.user = user;
        this.msg = msg;
        this.isSuccess = isSuccess;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public Observable<RegisterContract.Model> register(String phone, String password, String repassword) {
        return wanAndroidApi.register(phone,password,repassword)
                .map(baseResponse -> (RegisterContract.Model)new RegisterModel(phone,baseResponse.getErrorMsg(),baseResponse.getErrorCode()==0?true:false))
                .toObservable();
    }
}
