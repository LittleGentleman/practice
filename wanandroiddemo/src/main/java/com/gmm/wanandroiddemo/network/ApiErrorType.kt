package com.gmm.wanandroiddemo.network

import android.content.Context
import com.gmm.wanandroiddemo.R

/**
 * 枚举
 */
enum class ApiErrorType(val code:Int,private val messageID:Int) {
    INTERNET_SERVER_ERROR(500, R.string.service_error),//500：服务器异常
    BAD_GATEWAY(502, R.string.service_error),//502：网关异常
    NOT_FOUND(404,R.string.not_found),//404：请求资源找不到
    CONNECT_TIMEOUT(408,R.string.timeout),//408：网络请求超时
    NETWORK_NOT_CONNECT(499,R.string.network_error),//499:网络连接异常
    UNKNOWN_ERROR(700,R.string.unknown);//700：未知错误

    private val DEFAULT_CODE = 1

    fun getApiError(context: Context):ApiError{//每一项都可以调用这个函数
        return ApiError(DEFAULT_CODE,context.getString(messageID))
    }
}