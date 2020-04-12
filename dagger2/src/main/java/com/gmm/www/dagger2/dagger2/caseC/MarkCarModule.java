package com.gmm.www.dagger2.dagger2.caseC;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明: 用来提供依赖对象
 */
@Module
public class MarkCarModule {
    public MarkCarModule() {

    }

    /**
     * 2、同时我们需要对依赖提供方做出修改
     * @return
     */
    @Engine.QualifierA
    @Provides
    Engine provideEngineA() {
        return new Engine("gearA");
    }

    @Engine.QualifierB
    @Provides
    Engine provideEngine() {
        return new Engine("gearB");
    }
}
