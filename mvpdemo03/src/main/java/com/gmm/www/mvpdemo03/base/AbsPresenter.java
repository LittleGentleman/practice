package com.gmm.www.mvpdemo03.base;

/**
 * @author:gmm
 * @date:2020/3/14
 * @类说明:
 */
public abstract class AbsPresenter<M extends BaseModel,V extends BaseView> {
    protected M mModel;
    protected V mView;

    //外部注入
    public abstract void setModel(M mModel);

    //外部注入
    public abstract void setView(V mView);
}
