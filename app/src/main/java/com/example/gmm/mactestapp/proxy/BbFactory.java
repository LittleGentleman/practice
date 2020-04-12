package com.example.gmm.mactestapp.proxy;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明: 真实角色
 */
public class BbFactory implements  WomanToolsFactory{

    @Override
    public void saleWomanTools(float length) {
        System.out.println("按需求定制了一个高度为" + length + "的男model");
    }
}
