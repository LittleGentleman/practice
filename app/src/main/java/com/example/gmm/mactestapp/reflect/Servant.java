package com.example.gmm.mactestapp.reflect;

/**
 * @author:gmm
 * @date:2020/2/3
 * @类说明:
 */
public class Servant {
    public boolean service(String message) {
        System.out.println(message + "，我的手法很好！");
        return true;
    }

    @Override
    public String toString() {
        return "我是14号";
    }
}
