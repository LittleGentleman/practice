package com.gmm.www.mvpdemo02.network.api;

import com.gmm.www.mvpdemo02.network.bean.BaseResponse;
import com.gmm.www.mvpdemo02.network.bean.DataBean;
import com.gmm.www.mvpdemo02.network.bean.ProjectItem;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author:gmm
 * @date:2020/3/13
 * @类说明:
 */
public interface WanAndroidApi {
    //注解里传入 网络请求 的部分URL地址
    @GET("project/tree/json")
    //getProject()是接收网络请求数据的方式
    //RxJava 方式：Observable<..> 接口形式
    Observable<BaseResponse<List<DataBean>>> getProject();

    @GET("project/list/{pageIndex}/json")
    Observable<BaseResponse<ProjectItem>> getPorjectItem(@Path("pageIndex") int pageIndex, @Query("cid") int cid);

    @POST("user/register")
    @FormUrlEncoded
    Maybe<BaseResponse> register(@Field("username") String username,@Field("password") String password,@Field("repassword") String repassword);

    @POST("user/login")
    @FormUrlEncoded
    Maybe<BaseResponse> login(@Field("username") String username,@Field("password") String password);
}
