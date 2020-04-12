package com.gmm.www.mvpdagger2demo.base;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */
public abstract class AbsPresenter<M extends BaseModel,V extends BaseView> {

    protected M mModel;

    protected V mView;
}
