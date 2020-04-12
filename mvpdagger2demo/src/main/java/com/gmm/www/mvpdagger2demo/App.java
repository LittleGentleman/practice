package com.gmm.www.mvpdagger2demo;

import android.app.Application;

import com.gmm.www.mvpdagger2demo.di.AppComponent;
import com.gmm.www.mvpdagger2demo.di.AppModule;
import com.gmm.www.mvpdagger2demo.di.DaggerAppComponent;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */
public class App extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
