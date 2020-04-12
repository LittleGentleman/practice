package com.gmm.wanandroiddemo.api


import com.gmm.wanandroiddemo.bean.BaseResponse
import com.gmm.wanandroiddemo.bean.LoginBean
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Observable:被观察者
 */
interface WanAndroidApi {

    /**
     * 登录接口
     */
    @POST("/user/login")
    @FormUrlEncoded //表单
    fun login(@Field("username") username:String,
              @Field("password") password:String): Observable<BaseResponse<LoginBean>>
//    fun login(@Body map: HashMap<String,Any?>):Observable<BaseResponse<LoginBean>>

    /**
     * 注册接口
     */
    @POST("/user/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("repassword") repassword:String):Observable<BaseResponse<LoginBean>>
}