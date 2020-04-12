package com.gmm.www.lambdademo.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author:gmm
 * @date:2020/3/5
 * @类说明:
 */

@Retention(RetentionPolicy.RUNTIME)
@interface Persons {
    Person[] value();
}


@Repeatable(Persons.class)
@interface Person {
    String role() default "";
}

@Person(role = "父亲")
@Person(role = "儿子")
@Person(role = "丈夫")
@Person(role = "老师")
public class Human {

    public static void main(String[] args) {
        Class<Human> humanClass = Human.class;
        if (humanClass.isAnnotationPresent(Persons.class)){
            Persons persons = humanClass.getAnnotation(Persons.class);
            Person[] person = persons.value();

            for(Person p : person) {
                System.out.println("Human 使用了@Persons注解,value:" + p.role());
            }

        }
    }
}
