package com.gmm.www.dagger2.dagger2.caseC;

import dagger.Component;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明:注入器
 */
@Component(modules = MarkCarModule.class)
public interface CarComponent {
    void inject(Car car);
}
