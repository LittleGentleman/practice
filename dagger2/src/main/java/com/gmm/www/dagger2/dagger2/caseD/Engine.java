package com.gmm.www.dagger2.dagger2.caseD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明:依赖提供方
 *
 *
 */

public class Engine {

    //多个同类型的 要自定义多个注解  并且使用@Qualifier元注解
//    @Qualifier
//    @Retention(RetentionPolicy.RUNTIME)
//    public @interface QualifierA{}
//
//    @Qualifier
//    @Retention(RetentionPolicy.RUNTIME)
//    public @interface QualifierB{}

    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CarScope{}

    private String name;

    public Engine(){

    }

    public Engine(String name) {
        this.name = name;
        System.out.println("Engine Create:" + name);
    }

    public void run() {
        System.out.println("引擎跑起来了~~~~~~");
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                '}';
    }
}
