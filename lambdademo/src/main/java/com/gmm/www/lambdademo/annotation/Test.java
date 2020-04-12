package com.gmm.www.lambdademo.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author:gmm
 * @date:2020/3/5
 * @类说明: 定义注解
 */

//@Retention 注解的的存活时间
//@Inherited Inherited 是继承的意思，但是它并不是说注解本身可以继承，而是说如果一个超类被 @Inherited 注解过的注解进行注解的话，那么如果它的子类没有被任何注解应用的话，那么这个子类就继承了超类的注解。
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Test {
    String value();
}
