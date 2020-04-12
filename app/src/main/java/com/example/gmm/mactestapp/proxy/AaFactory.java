package com.example.gmm.mactestapp.proxy;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明: 真实角色
 */
public class AaFactory implements ManToolsFactory {
    @Override
    public void saleManTools(String size) {
        System.out.println("按需求定制了一个size为" + size + "的女model");
    }
}
