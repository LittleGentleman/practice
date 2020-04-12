package com.example.gmm.mactestapp.generic;

import java.io.Serializable;

/**
 * 类型变量的限定
 */
public class TypeLimit {

    public static <T extends Comparable&Serializable> T min(T a, T b) {
        if (a.compareTo(b) > 0)
            return a;
        else
            return b;
    }

    /**
     * Test 必须实现Comparable，min方法才可以正常调用，因为min限定了泛型类型必须实现了Comparable
     */
    static class Test implements Comparable,Serializable {

        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }

    public static void main(String[] args) {
        min(new Test(),new Test());
    }

}
