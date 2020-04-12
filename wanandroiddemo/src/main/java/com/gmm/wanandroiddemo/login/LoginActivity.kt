package com.gmm.wanandroiddemo.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.gmm.wanandroiddemo.PreferenceUtil
import com.gmm.wanandroiddemo.R
import com.gmm.wanandroiddemo.base.BaseActivity
import com.gmm.wanandroiddemo.base.IBasePresenter
import com.gmm.wanandroiddemo.bean.LoginBean
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * retrofit+rxjava  做网络请求    mvp模式
 *
 * RxAppCompatActivity rxjava生命周期 防止内存泄露
 */

class LoginActivity : BaseActivity<LoginPresenter>(), LoginView {

    companion object {
        //伴生对象，一个类只能有一个 相当于类的静态变量  代替static
        val TAG = "wanandroid"
    }

    //by 委托
    private var preference by PreferenceUtil("name","kotlin")

//        lateinit var presenter: LoginPresenter
//    private val presenter: LoginPresenter by lazy {
//        LoginPresenterImpl(this)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_register.setOnClickListener(onClickListener)
        btn_login.setOnClickListener(onClickListener)

        Log.e(TAG,"name=$preference")


    }

    override fun createPresenter(): LoginPresenter {
        presenter = LoginPresenterImpl(this)
        return presenter
    }


    private val onClickListener: View.OnClickListener = View.OnClickListener {
        when (it.id) {
            R.id.btn_register -> {
                presenter.register(this, et_username.text.toString(), et_password.text.toString(), et_password.text.toString())
            }
            R.id.btn_login -> {
                presenter.login(this, et_username.text.toString(), et_password.text.toString())
            }
        }

    }

    override fun loginSuccess(data: LoginBean) {
        Log.e(TAG, "loginInfo:$data")
        Toast.makeText(this@LoginActivity, data.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun loginFail(errorMessage: String) {
        Log.e(TAG, "login fail")
    }

    override fun registerSuccess(loginBean: LoginBean) {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show()
    }

    override fun registerFail(errorMessage: String) {
        Log.e(TAG, errorMessage)
    }

}
