package com.gmm.wanandroiddemo.login

import android.content.Context
import com.gmm.wanandroiddemo.bean.LoginBean

/**
 * presenter 通过model获取数据，然后回调给view
 */
class LoginPresenterImpl(private val loginView: LoginView) : LoginPresenter,LoginPresenter.OnLoginListener,LoginPresenter.OnRegisterListener {

    private val loginModel = LoginModelImpl()

    override fun login(context: Context, username: String, password: String) {
        loginModel.login(context,username,password,this)
    }

    override fun register(context: Context, username: String, password: String, repassword: String) {
        loginModel.register(context,username,password,repassword,this)
    }

    override fun loginSuccess(loginBean: LoginBean) {
        loginView.loginSuccess(loginBean)
    }

    override fun loginFail(errorMessage: String) {
        loginView.loginFail(errorMessage)
    }

    override fun registerSuccess(loginBean: LoginBean) {
        loginView.registerSuccess(loginBean)
    }

    override fun registerFail(errorMessage: String) {
        loginView.registerFail(errorMessage)
    }
}