package com.gmm.wanandroiddemo.network

import android.content.Context
import android.nfc.Tag
import android.util.Log
import com.gmm.wanandroiddemo.bean.BaseResponse
import com.gmm.wanandroiddemo.view.LoadingDialog
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Observer:观察者
 */
abstract class ApiResponse<T>(private val context:Context):Observer<BaseResponse<out T>> {
    private var isShowLoading:Boolean = false

    abstract fun success(data:T)
    abstract fun fail(statusCode:Int,error:ApiError)

    override fun onSubscribe(d: Disposable) {
        //加载
        if (!isShowLoading) {
            LoadingDialog.show(context)
            isShowLoading = true
        }
    }

    override fun onNext(t: BaseResponse<out T>) {
        success(t.data)
    }

    override fun onError(e: Throwable) {
        //取消加载
        LoadingDialog.cancel()
        isShowLoading = false

        if (e is HttpException) {
            val apiError:ApiError = when(e.code()){
                ApiErrorType.INTERNET_SERVER_ERROR.code ->
                    ApiErrorType.INTERNET_SERVER_ERROR.getApiError(context)
                ApiErrorType.BAD_GATEWAY.code ->
                    ApiErrorType.BAD_GATEWAY.getApiError(context)
                ApiErrorType.NOT_FOUND.code ->
                    ApiErrorType.NOT_FOUND.getApiError(context)
                else->{
//                    Log.e("tag","error:${e.response()}")
                    otherError(e)
                }

            }
            fail(e.code(),apiError)
            return
        }

        val apiErrorType:ApiErrorType = when(e){
            is UnknownHostException ->
                ApiErrorType.NETWORK_NOT_CONNECT
            is ConnectException ->
                ApiErrorType.NETWORK_NOT_CONNECT
            is SocketTimeoutException ->
                ApiErrorType.CONNECT_TIMEOUT
            else ->
                ApiErrorType.UNKNOWN_ERROR
        }
        fail(apiErrorType.code,apiErrorType.getApiError(context))
    }

    private fun otherError(e:HttpException) = Gson().fromJson(e.response().errorBody()?.charStream(),ApiError::class.java)

    override fun onComplete() {
        //取消加载
        LoadingDialog.cancel()
        isShowLoading = false
    }
}