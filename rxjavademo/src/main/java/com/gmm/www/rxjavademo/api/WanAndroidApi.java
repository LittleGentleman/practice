package com.gmm.www.rxjavademo.api;

import com.gmm.www.rxjavademo.bean.BaseResponse;
import com.gmm.www.rxjavademo.bean.ProjectBean;
import com.gmm.www.rxjavademo.bean.ProjectItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author:gmm
 * @date:2020/2/23
 * @类说明:
 */
public interface WanAndroidApi {

    //注解里注入 网络请求 的部分URL地址
    @GET("project/tree/json")
    //getProducts()是接受网络请求数据的方法
    //RxJava方式：Observable<..>接口形式
    Observable<BaseResponse<List<ProjectBean>>> getProjects();


    /**
     * Path是用来替换你路径里的条目的
     * Query 是作为url的参数的
     * @param pageIndex
     * @param cid
     * @return
     */
    @GET("project/list/{pageIndex}/json")
    Observable<ProjectItem> getProjectItem(@Path("pageIndex") int pageIndex, @Query("cid") int cid);
}
