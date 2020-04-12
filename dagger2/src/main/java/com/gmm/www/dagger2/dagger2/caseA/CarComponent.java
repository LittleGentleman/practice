package com.gmm.www.dagger2.dagger2.caseA;

import dagger.Component;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明:用于标注接口，是依赖需求方和依赖提供方之间的桥梁。被Component标注的接口
 * 在编译时会生成该接口的实现(DaggerCarComponent)
 * 这个CarComponent其实就是一个注入器，这里用来将Engine注入到Car中
 */

@Component
public interface CarComponent {
    void inject(Car car);//依赖需求方
}
