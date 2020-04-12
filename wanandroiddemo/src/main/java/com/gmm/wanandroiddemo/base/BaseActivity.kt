package com.gmm.wanandroiddemo.base

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

abstract class BaseActivity <P> : RxAppCompatActivity() where P:IBasePresenter{

    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun createPresenter():P
}