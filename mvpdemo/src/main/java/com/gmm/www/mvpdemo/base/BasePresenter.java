package com.gmm.www.mvpdemo.base;

/**
 * @author:gmm
 * @date:2020/3/10
 * @类说明:
 */
public interface BasePresenter {

    /**
     * 初始化数据
     */
    void start();


    /**
     * 销毁 尤其是销毁view （jetpack lifecycle 可以感知声明周期）
     * 否则，当view的声明周期结束后，Presenter会继续持有view的引用，从而引起泄露
     */
    void onDestroy();
}
