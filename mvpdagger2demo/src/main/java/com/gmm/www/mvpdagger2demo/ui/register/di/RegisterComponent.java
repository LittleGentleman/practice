package com.gmm.www.mvpdagger2demo.ui.register.di;

import com.gmm.www.mvpdagger2demo.di.AppComponent;
import com.gmm.www.mvpdagger2demo.ui.register.RegisterActivity;

import dagger.Component;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:* 注射器
 *  * 可以理解为快递员，那么他需要送的货物就是modules里面包含的包裹
 */
@Component(modules = RegisterModule.class,dependencies = AppComponent.class)
public interface RegisterComponent {

    /**
     * 把Component理解为快递员，那么他把包裹送给谁呢
     * 这里的inject方法的LoginActivity 就是送货的地址
     *
     * @param activity 目标容器
     * inject的参数。。。不能是父类，必须是你注入的那个内
     */
    void inject(RegisterActivity activity);
}
