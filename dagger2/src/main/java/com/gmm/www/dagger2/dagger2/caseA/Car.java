package com.gmm.www.dagger2.dagger2.caseA;


import javax.inject.Inject;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明: Car类是需求依赖方，依赖了Engine类；因此我们需要在
 * 类变量Engine上添加@Inject来告诉Dagger2来为自己提供依赖
 */
public class Car {

    /**
     * @Inject:@Inject有两个作用，一是用来标记需要依赖的变量，
     * 以此告诉Dagger2为它提供依赖（变量实例由dagger提供，不是自己创建）
     */
    @Inject
    Engine engine;

    public Car() {
        DaggerCarComponent.builder().build().inject(this);
    }

    public Engine getEngine() {
        return engine;
    }

    public static void main(String[] args) {
        Car car = new Car();
        System.out.println(car.getEngine());
        car.getEngine().run();
    }
}
