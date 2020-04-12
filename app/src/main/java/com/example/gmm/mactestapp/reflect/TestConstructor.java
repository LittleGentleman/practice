package com.example.gmm.mactestapp.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明:通过反射 获取全部的构造器
 */
public class TestConstructor {

    public void textConstructor() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String className = "com.example.gmm.mactestapp.reflect.Person";
        Class<Person> clazz = (Class<Person>) Class.forName(className);

        System.out.println("获取全部Constructor对象------------");
        Constructor<Person>[] constructors = (Constructor<Person>[]) clazz.getConstructors();
        for (Constructor<Person> constructor:
             constructors) {
            System.out.println(constructor);
        }

        System.out.println("获取某一个Constructor 对象，需要参数列表------");
        Constructor<Person> constructor = clazz.getConstructor(String.class,int.class);
        System.out.println(constructor);

        //调用构造器的newInstance() 方法创建对象
        System.out.println("调用构造器的 newInstance() 方法创建对象-------");
        Person obj = constructor.newInstance("GMM",25);
        System.out.println(obj.getName());
    }
}
