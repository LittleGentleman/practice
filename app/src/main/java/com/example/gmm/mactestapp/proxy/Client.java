package com.example.gmm.mactestapp.proxy;

import com.example.gmm.mactestapp.proxy.dynamic.MarkCompany;
import com.example.gmm.mactestapp.proxy.normal.Alvin;
import com.example.gmm.mactestapp.proxy.normal.Mark;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明: 访问者
 */
public class Client {

    public static void main(String[] args) {
        //静态代理模式  违反开闭原则 扩展能力差，可维护性差
//        ManToolsFactory factory = new AaFactory();
//        Mark mark = new Mark(factory);
//        mark.saleManTools("D");
//
//        WomanToolsFactory womanToolsFactory = new BbFactory();
//        Alvin alvin = new Alvin(womanToolsFactory);
//        alvin.saleWomanTools(10);

        //动态代理模式
        ManToolsFactory aFactory = new AaFactory();
        MarkCompany company = new MarkCompany();
        company.setFactory(aFactory);//为AaFactory代理

        //代理1
        ManToolsFactory employee1 = (ManToolsFactory) company.getProxyInstance();
        employee1.saleManTools("E");

        WomanToolsFactory bFactory = new BbFactory();
        company.setFactory(bFactory);//为BbFactory代理

        //代理2
        WomanToolsFactory employee2 = (WomanToolsFactory) company.getProxyInstance();
        employee2.saleWomanTools(7);

    }
}
