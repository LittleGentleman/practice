package com.example.gmm.mactestapp.reflect;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明:演示反射的使用
 */
public class ReflectDemo {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        //实例化对象的标准用法
        Servant servant = new Servant();
        servant.service("Hello");

        Class servantClass = Servant.class;
        Class servantClass2 = servant.getClass();
        Class servantClass3 = Class.forName("com.example.gmm.mactestapp.reflect.Servant");

        Servant servant1 = (Servant) servantClass3.newInstance();
        servant1.service("OK");
    }
}
