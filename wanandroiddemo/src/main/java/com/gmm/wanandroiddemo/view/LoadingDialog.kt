package com.gmm.wanandroiddemo.view

import android.app.Dialog
import android.content.Context
import com.gmm.wanandroiddemo.R

object LoadingDialog {//静态类
    private var dialog:Dialog? = null

    fun show(context: Context){
        cancel()
        dialog = Dialog(context)
//        dialog?.setContentView(R.layout.dialog_loading)
//        dialog?.setCancelable(false)
//        dialog?.setCanceledOnTouchOutside(false)
//        dialog?.show()
        //高阶函数 let  可以减少dialog？
        dialog?.let {
            it.setContentView(R.layout.dialog_loading)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            it.show()
        }
    }

    fun cancel(){
        dialog?.dismiss()//如果dialog不为null，就会调用dismiss
    }
}