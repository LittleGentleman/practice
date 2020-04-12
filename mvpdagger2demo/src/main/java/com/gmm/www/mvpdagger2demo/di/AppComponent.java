package com.gmm.www.mvpdagger2demo.di;

import android.app.Application;

import com.gmm.www.mvpdagger2demo.network.api.WanAndroidApi;
import com.gmm.www.mvpdagger2demo.network.di.ApiServiceModule;
import com.gmm.www.mvpdagger2demo.network.di.HttpModule;

import dagger.Component;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */
@Component(modules = {AppModule.class,HttpModule.class,ApiServiceModule.class})
public interface AppComponent {

    Application application();

    WanAndroidApi getWandroidApi();
}
