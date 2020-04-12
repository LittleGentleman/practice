package com.gmm.www.mvpdagger2demo.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author:gmm
 * @date:2020/3/15
 * @类说明:
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope { }
