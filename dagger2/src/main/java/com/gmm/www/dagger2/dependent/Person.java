package com.gmm.www.dagger2.dependent;

/**
 * @author:gmm
 * @date:2020/3/6
 * @类说明: 面向接口编程  依赖倒置原则（需求改动，减少代码改动） 控制反转（IoC）:反转了上层模块对于底层模块的依赖控制
 *
 * 依赖注入的方式：①构造函数注入 ②setter方法注入 ③接口注入
 */
public class Person implements DependentSetterInterface {

    private TrafficTool trafficTool;

    //构造函数注入
    public Person(TrafficTool trafficTool) {
        this.trafficTool = trafficTool;
    }

    //setter 方法注入
    public void setTrafficTool(TrafficTool trafficTool) {
        this.trafficTool = trafficTool;
    }

    //接口方式注入
    @Override
    public void set(TrafficTool trafficTool) {
        this.trafficTool = trafficTool;
    }

    private void goOut() {
        System.out.println("出门啦");
        trafficTool.drive();
    }

    public static void main(String[] args) {
        Person p = new Person(new Car());
//        Person p = new Person(new Bike());
//        p.setTrafficTool(new Bike());
        p.set(new Car());
        p.goOut();
    }


}
