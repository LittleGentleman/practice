package com.gmm.www.dagger2.dagger2.caseB;

import javax.inject.Inject;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明:假设当前这个依赖提供方是第三方的，不可以修改，也就是说不能添加@Inject注入标签
 */
public class Engine {
    private String name;

    Engine(){

    }

    Engine(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                '}';
    }

    public void run(){
        System.out.println("引擎转起来了~~~~~~~");
    }
}
