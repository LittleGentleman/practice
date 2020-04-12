package com.gmm.wanandroiddemo.login

import android.content.Context
import com.gmm.wanandroiddemo.api.WanAndroidApi
import com.gmm.wanandroiddemo.bean.LoginBean
import com.gmm.wanandroiddemo.network.ApiError
import com.gmm.wanandroiddemo.network.ApiResponse
import com.gmm.wanandroiddemo.network.HttpClient
import com.gmm.wanandroiddemo.network.NetworkScheduler

/**
 * 处理业务
 * 获取数据后，回调给presenter
 */
class LoginModelImpl : LoginModel {
    override fun login(context: Context, username: String, password: String, onLoginListener: LoginPresenter.OnLoginListener) {
        HttpClient.instance.getService(WanAndroidApi::class.java).login(username, password)
                .compose(NetworkScheduler.compose())
//                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribe(object : ApiResponse<LoginBean>(context) {
                    //匿名内部类写法
                    override fun success(data: LoginBean) {
                        onLoginListener.loginSuccess(data)
                    }

                    override fun fail(statusCode: Int, error: ApiError) {
                        onLoginListener.loginFail(error.message)
                    }

                })
    }

    override fun register(context: Context, username: String, password: String, repassword: String, onRegisterListener: LoginPresenter.OnRegisterListener) {
        HttpClient.instance.getService(WanAndroidApi::class.java).register(username,password,repassword)
                .compose(NetworkScheduler.compose())
//                .bindUntilEvent(this,ActivityEvent.DESTROY)
                .subscribe(object : ApiResponse<LoginBean>(context){
                    override fun success(data: LoginBean) {
                        onRegisterListener.registerSuccess(data)
                    }

                    override fun fail(statusCode: Int, error: ApiError) {
                        onRegisterListener.registerFail(error.message)
                    }

                })
    }

}