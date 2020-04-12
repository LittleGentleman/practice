package com.gmm.www.mvpdagger2demo.ui.login.di;

import com.gmm.www.mvpdagger2demo.ui.login.LoginContract;
import com.gmm.www.mvpdagger2demo.ui.login.LoginModel;
import com.gmm.www.mvpdagger2demo.ui.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 * 模块
 *  * 可以理解为快递包裹，这是一个容器
 *  * 下面以@Provides修饰的方法就是这个包裹包含的物品
 *  * 在Component接口中通过@Component(modules =
 *  * xxxx.class),将容器需要的商品封装起来，统一交给快递员（Component），
 *  * 让快递员统一送到目标容器中
 *  * Module编写技巧：
 *  * 1. dagger使用的目的在于更多的使用面向接口的编程
 *  * 2. 构造具体的类的实现，但是返回接口
 */
@Module
public class LoginModule {
    LoginContract.View view;

    /**
     * 初始化的时候传递进来，以便注入给P层
     * @param view
     */
    public LoginModule(LoginContract.View view){
        this.view = view;
    }

    @Provides
    LoginContract.View provideLoginView() {
        return this.view;
    }

    //    @Provides
//    这种写法是比较常见的，内部new一个对象返回
//    MainContract.Model provideLoginModel() {
//        return new RegisterModel();
//    }

//    返回类型和参数类型相同 报错
//    @Provides
//    RegisterModel provideLoginModel(RegisterModel loginModel) {
//        return loginModel;
//    }

    /**
     * 这种含有参数的写法会自动构造参数
     * 但是请注意参数里面的LoginModel 必须有@Inject的构造方法
     * 或者有@Provides 提供的实例
     *
     * @param loginModel 具体的，必须预先提供（@Inject）
     * @return 接口或者抽象的
     * 注意： 参数和返回惠州不能是相同的类
     */
    @Provides
    LoginContract.Model provideLoginModel(LoginModel loginModel){
        return loginModel;
    }

    @Provides
    LoginContract.Presenter providePresenter(LoginPresenter presenter){
        return presenter;
    }
}
