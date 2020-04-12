package com.gmm.www.mvpdagger2demo.di;

import android.app.Application;

import com.gmm.www.mvpdagger2demo.App;

import dagger.Module;
import dagger.Provides;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */

@Module
public class AppModule {

    private Application mApplication;

    public AppModule(App application) {
        this.mApplication = application;
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }
}
