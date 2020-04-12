package com.gmm.www.lambdademo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author:gmm
 * @date:2020/3/4
 * @类说明:
 */
public class Person {
    private String name;
    private int age;
    private Integer score;

    public Person() {
    }

    public Person(String name, int age, int score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }


    public static void main(String[] args) {
        //比较两个人的age大小
        PersonInterface<Person> personInterface = (t1, t2) ->
                            t1.getAge()>t2.getAge()?t1.getAge():t2.getAge();//如果放在{}里，需要在前面加上return

        PersonInterface<Person> personInterface1 = (t1, t2) -> {
            return t1.getScore().compareTo(t2.getScore());//大于返回1，等于返回0，小于返回-1
        };

        //:: 方法引用
        //java
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        //lambda 普通
        Consumer<String> consumer1 = s -> System.out.println(s);
        //lambda 方法引用
        Consumer<String> consumer2 = System.out::println;
        consumer2.accept("方法引用");


        Person p1 = new Person("James",21,91);
        Person p2 = new Person("Linda",23,92);
        Person p3 = new Person("Mike",19,89);
        List<Person> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        Collections.sort(list,(o1, o2) -> o1.getScore().compareTo(o2.getScore()));
        System.out.println(list);

        //必须是函数式接口，才能使用lambda，如果是class类，把class类放到函数式接口中（只有一个抽象函数的接口）
        Supplier<Person> p4 = Person::new;

    }
}
