package com.gmm.www.dagger2.dagger2.caseD;

import dagger.Module;
import dagger.Provides;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明: 通过@Module @Provides @Qualifier 实现 提供多个依赖
 */

@Module
public class MarkCarModule {
    public MarkCarModule() {
    }

//    @Engine.QualifierA
    @Engine.CarScope
    @Provides
    Engine provideEngineA(){
        return new Engine("gear");
    }

//    @Engine.QualifierB
//    @Provides
//    Engine provideEngineB(){
//        return new Engine("gearB");
//    }
}
