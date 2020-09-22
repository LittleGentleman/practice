package com.gmm.www.ipclibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author:gmm
 * @date:2020/4/23
 * @类说明:
 */
@Target(ElementType.TYPE)//自定义注解给类使用
@Retention(RetentionPolicy.RUNTIME)//自定义注解在runtime反射使用
public @interface ServiceId {
    String value();
}
