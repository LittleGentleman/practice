package com.gmm.www.dagger2.dagger2.caseD;

import javax.inject.Inject;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明:依赖需求方
 *
 * @Scope 限定作用域，实现局部单例的
 * 首先需要通过@Scope定义一个CarScope注解
 */
public class Car {

    //同类型的变量，使用@Qualifier
//    @Engine.QualifierA
    @Inject
    Engine engineA;

//    @Engine.QualifierB
    @Inject
    Engine engineB;

    public Car() {
        DaggerCarComponent.builder().markCarModule(new MarkCarModule()).build().inject(this);
    }

    public Engine getEngineA() {
        return engineA;
    }

    public Engine getEngineB() {
        return engineB;
    }

    public static void main(String[] args) {
        Car car = new Car();
        System.out.println(car.getEngineA());
        System.out.println(car.getEngineB());
    }
}
