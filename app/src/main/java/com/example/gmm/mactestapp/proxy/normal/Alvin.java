package com.example.gmm.mactestapp.proxy.normal;

import com.example.gmm.mactestapp.proxy.WomanToolsFactory;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明:
 */
public class Alvin implements WomanToolsFactory {

    private WomanToolsFactory womanToolsFactory;

    public Alvin(WomanToolsFactory womanToolsFactory) {
        this.womanToolsFactory = womanToolsFactory;
    }

    private void doSthAfter() {
        System.out.println("精美包装，快递一条龙服务");
    }

    private void doSthBefore() {
        System.out.println("根据需求，进行市场调研和产品分析");
    }

    @Override
    public void saleWomanTools(float length) {
        doSthBefore();
        womanToolsFactory.saleWomanTools(length);
        doSthAfter();
    }
}
