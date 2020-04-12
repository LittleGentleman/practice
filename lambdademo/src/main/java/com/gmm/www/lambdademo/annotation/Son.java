package com.gmm.www.lambdademo.annotation;

/**
 * @author:gmm
 * @date:2020/3/5
 * @类说明:
 */

public class Son extends Father {

    public Son(String name) {
        super(name);
    }

    public static void main(String[] args) {

        //获取Son类的class对象
        Class<Son> sonClass = Son.class;
        //判断Father类是不是Son的父注解类
        if (sonClass.isAnnotationPresent(Rich.class)) {
            Rich inheritedTest = sonClass.getAnnotation(Rich.class);
            String value = inheritedTest.value();
            System.out.println("注解Rich的value：" + value);
        }
    }
}
