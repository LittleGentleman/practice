package com.gmm.wanandroiddemo.bean

/**
 * BaseResponse 返回的数据容器，存放数据类型为泛型T的data，errorCode 和 errorMessage
 */
data class BaseResponse<out T>(val errorCode:Int,val errorMessage:String,
                           val data:T) //out 协变 不能保存，只能获取  所以data使用val关键字