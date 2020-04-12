package com.example.gmm.mactestapp.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明: 动态代理类  用于生成代理对象
 */
public class MarkCompany implements InvocationHandler {

    //持有真实对象
    private Object factory;

    public Object getFactory() {
        return factory;
    }

    public void setFactory(Object factory) {
        this.factory = factory;
    }

    //通过Proxy获得动态代理对象
    public Object getProxyInstance() {
        /**
         * classloader: 真实对象的 类加载器
         *
         */
        return Proxy.newProxyInstance(factory.getClass().getClassLoader(),
                factory.getClass().getInterfaces(),this);
    }

    /**
     *通过动态代理对象方法进行增强
     * @param proxy 代理对象
     * @param method 真实对象中的方法
     * @param args 真实对象方法中需要传入的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        doSthBefore();

        Object result = method.invoke(factory, args);

        doSthAfter();

        return result;
    }

    private void doSthAfter() {
        System.out.println("精美包装，快递一条龙服务");
    }

    private void doSthBefore() {
        System.out.println("根据需求，进行市场调研和产品分析");
    }

}