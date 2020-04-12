package com.example.gmm.mactestapp.reflect;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明:
 */
public class Person {
    String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("this is setName()!");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        System.out.println("this is setAge()!");
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    //私有方法
    private void privateMethod() {
        System.out.println("this is private method!");
    }
}
