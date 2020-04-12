package com.gmm.wanandroiddemo.login

import android.content.Context
import com.gmm.wanandroiddemo.base.IBasePresenter
import com.gmm.wanandroiddemo.bean.LoginBean

interface LoginPresenter : IBasePresenter{
    fun login(context: Context, username:String, password:String)

    fun register(context: Context, username: String, password: String,repassword: String)

    interface OnLoginListener{
        fun loginSuccess(loginBean: LoginBean)

        fun loginFail(errorMessage: String)
    }

    interface OnRegisterListener{
        fun registerSuccess(loginBean: LoginBean)

        fun registerFail(errorMessage: String)
    }
}