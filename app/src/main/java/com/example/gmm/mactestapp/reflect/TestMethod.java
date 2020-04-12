package com.example.gmm.mactestapp.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明: 反射获取类中的方法
 */
public class TestMethod {
    public void testMethod() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class clazz = Class.forName("com.example.gmm.mactestapp.reflect.Person");

        System.out.println("获取clazz对应类中的所有方法，不能获取private方法，且获取从父类继承拉埃的所有方法");
        Method[] methods = clazz.getMethods();
        for (Method method :
                methods) {
            System.out.println(" " + method.getName() + "()");
        }
        System.out.println("");
        System.out.println("-------------------------------");

        System.out.println("获取所有方法，包括私有方法，所有声明的方法，都可以获取到，且只获取当前类的方法");
        methods = clazz.getDeclaredMethods();
        for (Method method :
                methods) {
            System.out.println(" " + method.getName() + "()");
        }
        System.out.println("");
        System.out.println("-------------------------------");

        System.out.println("获取指定方法，需要参数名和参数列表，无参则不需要写");
        //方法public void setName(String name)
        Method method = clazz.getDeclaredMethod("setName", String.class);
        System.out.println(method);
        System.out.println("---");

        //方法public void setAge(int age)
        method = clazz.getDeclaredMethod("setAge", int.class);
        System.out.println(method);
        System.out.println("-------------------------------");


        System.out.println("执行方法，第一个参数表示执行哪个对象的方法，剩下的参数是执行方法时需要传入的参数");
        Object object = clazz.newInstance();
        method.invoke(object,25);

        //私有方法的执行，必须在调用invoke之前加上一句method.setAccessible(true)
        method = clazz.getDeclaredMethod("privateMethod");
        System.out.println(method);
        System.out.println("-------------------------------");
        System.out.println("执行私有方法");
        method.setAccessible(true);
        method.invoke(object);
    }
}
