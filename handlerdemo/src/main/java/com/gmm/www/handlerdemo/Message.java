package com.gmm.www.handlerdemo;

/**
 * @author:gmm
 * @date:2020/5/10
 * @类说明:
 */
public class Message {
    String obj;
    Handler target;

    public Message(String obj) {
        this.obj = obj;
    }

    public String toString(){
        return obj.toString();
    }
}
