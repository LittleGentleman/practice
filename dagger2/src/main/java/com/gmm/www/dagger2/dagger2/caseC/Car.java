package com.gmm.www.dagger2.dagger2.caseC;

import javax.inject.Inject;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明:依赖需求方，需要依赖两个Engine变量时，使用@Qulifier
 */
public class Car {
    /**
     * 3、接下来依赖需求方Car类同样需要修改
     */
    @Engine.QualifierA
    @Inject
    Engine engineA;

    @Engine.QualifierB
    @Inject
    Engine engineB;

    public Car() {
        //markCarModule(new MarkCarModule() 告诉注入器CarComponent把MarkCarModule提供的依赖注入到Car类中
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
