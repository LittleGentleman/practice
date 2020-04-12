package com.gmm.www.lambdademo.annotation;

/**
 * @author:gmm
 * @date:2020/3/5
 * @类说明:
 */

@Rich(value = "富一代")
public class Father {
    private String name;

    public Father() {
    }

    public Father(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
