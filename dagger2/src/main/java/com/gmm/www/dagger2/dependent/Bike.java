package com.gmm.www.dagger2.dependent;

/**
 * @author:gmm
 * @date:2020/3/6
 * @类说明:
 */
public class Bike implements TrafficTool {
    @Override
    public void drive() {
        System.out.println("自行车");
    }
}
