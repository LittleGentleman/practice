package com.gmm.wanandroiddemo.network

import com.gmm.wanandroiddemo.BuildConfig
import com.gmm.wanandroiddemo.api.WanAndroidApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *Retrofit 单例类
 */
class HttpClient private constructor() {//单例类

    lateinit var retrofit: Retrofit

    private object Holder {
        val INSTANCE = HttpClient()
    }

    companion object {
        //通过伴生对象，实现单例模式  相当于java的static
        val instance by lazy { Holder.INSTANCE }
    }

    fun createRetrofit() {
//        val okHttpClient = OkHttpClient().newBuilder()
//                .addInterceptor(HttpLoggingInterceptor().setLevel(
//                        if (BuildConfig.DEBUG)
//                            HttpLoggingInterceptor.Level.BODY
//                        else
//                            HttpLoggingInterceptor.Level.NONE
//                ))//添加拦截器
//                .build()

        val okHttpClient = OkHttpClient().newBuilder().apply {
            //高阶函数
            addInterceptor(HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE
                    ))
        }.build()

        retrofit = Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//gson解析
                .client(okHttpClient)
                .build()


    }

    fun <T> getService(service: Class<T>):T = retrofit.create(service)
}