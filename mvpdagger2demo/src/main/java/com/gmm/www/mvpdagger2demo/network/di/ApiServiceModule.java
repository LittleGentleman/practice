package com.gmm.www.mvpdagger2demo.network.di;

import com.gmm.www.mvpdagger2demo.network.api.WanAndroidApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import retrofit2.Retrofit;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明: 对应的是普通MVP中的HttpUtil类
 * 通过使用依赖注入的方式，提供retrofit  @Module @Provide 配合，实现依赖注入和提供
 */

@Module
public class ApiServiceModule {

    @Provides
    HttpUrl provideBaseUrl() {
        return HttpUrl.parse("https://www.wanandroid.com/");
    }

    @Provides
    WanAndroidApi provideWanAndroidApi(Retrofit retrofit) {
        return retrofit.create(WanAndroidApi.class);
    }
}
