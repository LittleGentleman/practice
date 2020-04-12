package com.gmm.www.mvpdagger2demo.network.api;

import com.gmm.www.mvpdagger2demo.network.bean.BaseResponse;
import com.gmm.www.mvpdagger2demo.network.bean.ProjectBean;
import com.gmm.www.mvpdagger2demo.network.bean.ProjectItem;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import okhttp3.HttpUrl;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */


public interface WanAndroidApi {

    //注解里传入 网络请求 的部分URL地址
    @GET("project/tree/json")
    //getProject()是接收网络请求数据的方法
    //RxJava 方法：Observable<..> 接口形式
    Observable<ProjectBean> getProject();

    @GET("project/list/{pageIndex}/json")
    Observable<ProjectItem> getProjectItem(@Path("pageIndex")int pageIndex, @Query("cid") int cid);

    @POST("user/register")
    @FormUrlEncoded
    Maybe<BaseResponse> register(@Field("username")String username,@Field("password")String password,@Field("repassword")String repassword);

    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse> login(@Field("username")String username,@Field("password")String password);
}
