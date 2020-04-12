package com.gmm.wanandroiddemo.login

import android.content.Context
import com.gmm.wanandroiddemo.bean.LoginBean
import io.reactivex.Observer

/**
 * 处理业务
 */
interface LoginModel {
    //返回一个LoginBean数据，所以用Observer观察者
    fun login(context: Context, username: String,password: String, onLoginListener: LoginPresenter.OnLoginListener)

    fun register(context: Context, username: String,password: String,repassword: String, onRegisterListener: LoginPresenter.OnRegisterListener)


}