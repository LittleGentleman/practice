package com.gmm.www.dagger2.dagger2.caseA;

import javax.inject.Inject;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明:Engine类是依赖提供方，因此我们需要在它的构造函数上添加@Inject
 */
public class Engine {

    /**
     * 二是用来标记构造器，Dagger2通过@Inject注解可以在需要这个类实例的时候来找到
     * 这个构造函数并把实例构造出来，以此为被@Inject标记了的变量提供依赖
     */
    @Inject  //@Inject只能标注一个构造函数
    Engine() {

    }

    @Override
    public String toString() {
        return "Engine{}";
    }

    public void run(){
        System.out.println("引擎转起来了~~~~");
    }
}
