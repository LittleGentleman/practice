package com.gmm.www.mvpdemo03.network.api;

import com.gmm.www.mvpdemo03.network.bean.BaseResponse;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author:gmm
 * @date:2020/3/14
 * @类说明:
 */
public interface WanAndroidApi {

    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse> login(@Field("username") String username,@Field("password") String password);
}
