package com.example.gmm.mactestapp.proxy.normal;

import com.example.gmm.mactestapp.proxy.ManToolsFactory;
import com.example.gmm.mactestapp.proxy.WomanToolsFactory;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明: 静态代理角色  内部持有真实对象
 */
public class Mark implements ManToolsFactory,WomanToolsFactory {

    //包含真实的对象
    public ManToolsFactory factory;

    public Mark(ManToolsFactory factory) {
        this.factory = factory;
    }

    private void doSthAfter() {
        System.out.println("精美包装，快递一条龙服务");
    }

    private void doSthBefore() {
        System.out.println("根据需求，进行市场调研和产品分析");
    }

    @Override
    public void saleManTools(String size) {
        doSthBefore();
        factory.saleManTools(size);
        doSthAfter();
    }

    @Override
    public void saleWomanTools(float length) {

    }
}
