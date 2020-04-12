package com.gmm.www.dagger2.dagger2.caseB;

import javax.inject.Inject;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明:如果Eggine类是我们无法修改的呢？这时候就需要@Module和@Provide上场了。
 */
public class Car {
    /**
     * 我们提到@Inject和@Module都可以提供依赖，那如果我们即在构造器上通过标记@Inject提
     * 供依赖，也通过@Module提供依赖，Dagger2会如何选择呢？具体规则如下：
     *
     * 步骤1：首先查找@Module标注的类中是否存在提供依赖的方法。
     * 步骤2：若存在提供依赖的方法，查看该方法是否存在参数
     * a:若存在参数，则按从步骤1开始一次初始化每个参数；
     * b:若不存在，则直接初始化该类实例，完成一次依赖注入
     *
     * 步骤3：若不存在提供依赖的方法，则查找@Inject标注的构造函数，看构造函数是否存在参数。
     * a：若存在参数，则从步骤1开始依次初始化每一个参数
     * b：若不存在，则直接初始化该类实例，完成一次依赖注入
     */

    @Inject
    Engine engine;

    public Car() {
        //.markCarModule(new MarkCarModule()相当于告诉了注入器DaggerCarComponent把MarkCarModule提供的依赖注入到Car类中
        DaggerCarComponent.builder().markCarModule(new MarkCarModule()).build().inject(this);
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
