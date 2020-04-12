package com.example.gmm.mactestapp.reflect;

import java.lang.reflect.Field;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明: 通过反射获取字段
 */
public class TestField {

    public void testField() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        String className = "com.example.gmm.mactestapp.reflect.Person";
        Class clazz = Class.forName(className);

        System.out.println("获取公有和私有的所有字段，但不能获取父类字段");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field :
                fields) {
            System.out.println(" " + field.getName());
        }
        System.out.println();
        System.out.println("------------------------------");


        System.out.println("获取指定字段");
        Field field = clazz.getDeclaredField("name");
        System.out.println(field.getName());

        Person person = new Person("ABC",12);
        System.out.println("获取指定字段的值");
        Object val = field.get(person);
        System.out.println(field.getName() + "=" + val);

        System.out.println("设置指定对象指定字段的值");
        field.set(person,"abc");
        System.out.println(field.getName() + "=" + person.getName());

        System.out.println("字段是私有的，不管是读值还是写值，都必须先调用setAccess(true)方法");
        Field ageField = clazz.getDeclaredField("age");
        ageField.setAccessible(true);//私有字段可访问
        Object val2 = ageField.get(person);
        System.out.println(ageField.getName() + "=" + val2);
    }
}
