package com.gmm.www.dagger2.dagger2.caseB;

import dagger.Component;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明: 注入器，编译是会生成实现类DaggerCarComponent
 */
@Component(modules = MarkCarModule.class) //用来告诉dagger2提供依赖对象的是MarkCarModule这个类
public interface CarComponent {
    void inject(Car car);//为Car注入依赖
}
