package com.gmm.www.dagger2.dagger2.caseB;

import dagger.Module;
import dagger.Provides;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明:用于标注提供依赖的类。使用这个Module类来生成依赖对象
 * @Module 和 @Provide 搭配来用
 */

@Module
public class MarkCarModule {

    public MarkCarModule() {
    }

    /**
     * 用于标注Module所标注的类中的方法，该方法在需要提供依赖是被调用，从而把预先提供好的
     * 对象当做依赖 给标注了@Inject的变量 赋值
     *
     * @return
     */
    @Provides  //标注具体提供依赖对象的方法
    Engine provideEngine(){
        return new Engine("gear");
    }

}
