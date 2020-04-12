package com.gmm.www.mvpdemo.base;

/**
 * @author:gmm
 * @date:2020/3/10
 * @类说明:
 */
public interface BaseView<T> {

    /**
     * 把p层和view层 关联起来
     * @param presenter
     */
    void setPresenter(T presenter);
}
