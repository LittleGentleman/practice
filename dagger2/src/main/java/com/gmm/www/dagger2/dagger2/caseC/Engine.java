package com.gmm.www.dagger2.dagger2.caseC;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author:gmm
 * @date:2020/3/7
 * @类说明:依赖提供方 假设当前类为第三方的类
 */
public class Engine {

    /**
     * @Qualifier
     * 用于自定义注解，也就是说@Qualifier就如同Java提供的几种基本元注解一样用来标记
     * 注解类。我们在使用@Module来标注提供依赖的方法时，方法名我们是可以随便定义的（
     * 虽然我们定义方法名一般以provide开头，但这并不是强制的，只是为了增加可读性而已）。
     * 那么Dagger2怎么知道是为谁提供依赖呢？答案就是返回值的类型，Dagger2根据返回值的类型
     * 来决定为哪个被@Inject标记了的变量赋值。但是问题来了，一旦有多个一样的返回类型
     * Dagger2就懵逼了。@Qualifier的存在正是为了解决这个问题，我们使用@Qualifier来
     * 定义自己的注解，然后通过自定义的注解去标注提供依赖的方法和依赖需求方（也就是被@Inject
     * 标注的变量），这样Dagger2就知道为谁提供依赖了。------一个更为简单的定义：当
     * 类型不足以鉴别一个依赖的时候，我们就可以使用这个注解标识
     * 1、使用@Qulifier定义两个注解 QualifierA / QualifierB
     */
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface QualifierA{}

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface QualifierB{}

    private String name;

    public Engine() {
    }

    public Engine(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println("引擎运行起来了~~~~~");
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                '}';
    }
}
