package com.gmm.wanandroiddemo.login

import com.gmm.wanandroiddemo.bean.LoginBean

interface LoginView {
    fun loginSuccess(loginBean: LoginBean)

    fun loginFail(errorMessage: String)

    fun registerSuccess(loginBean: LoginBean)

    fun registerFail(errorMessage: String)
}