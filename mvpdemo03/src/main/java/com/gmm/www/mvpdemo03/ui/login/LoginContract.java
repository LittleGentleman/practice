package com.gmm.www.mvpdemo03.ui.login;

import com.gmm.www.mvpdemo03.base.BaseModel;
import com.gmm.www.mvpdemo03.base.BasePresenter;
import com.gmm.www.mvpdemo03.base.BaseView;

import io.reactivex.Observable;

/**
 * @author:gmm
 * @date:2020/3/14
 * @类说明:
 */
public interface LoginContract {

    interface Presenter extends BasePresenter {
        void login(String phone,String password);
    }

    interface View extends BaseView<Presenter> {
        void loginSuccess(String result);
    }

    interface Model extends BaseModel {
        Observable<String> login(String phone,String password);
    }
}
