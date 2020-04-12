package com.gmm.www.mvpdemo.ui.login;

import com.gmm.www.mvpdemo.base.BasePresenter;
import com.gmm.www.mvpdemo.base.BaseView;

/**
 * @author:gmm
 * @date:2020/3/10
 * @类说明:方便管理接口
 */
public interface LoginContact {
    /**
     * 这里没有Model层，Presenter层充当了Model层的职责
     */
    interface Presenter extends BasePresenter{
        /**
         * 登录
         * @param mobile
         * @param password
         */
        void login(String mobile,String password);
    }

    interface View extends BaseView<Presenter>{
        /**
         * 登录成功
         * @param result
         */
        void loginSuccess(String result);
    }
}
