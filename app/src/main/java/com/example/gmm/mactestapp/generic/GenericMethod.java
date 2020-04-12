package com.example.gmm.mactestapp.generic;

/**
 * 泛型方法
 */
public class GenericMethod {

    /**
     * 泛型方法  <T>表明这是一个泛型方法
     * @param a
     * @param <T>
     * @return
     */
    public static  <T> T genericMethod(T...a){
        return  a[a.length/2];
    }

    /**
     *普通方法
     * @param x
     * @param y
     */
    public void test(int x,int y) {
        System.out.println(x+y);
    }

    public static void main(String[] args) {
        GenericMethod genericMethod = new GenericMethod();
        genericMethod.test(23,343);
        System.out.println(genericMethod.<String>genericMethod("mark","av","lance"));
        System.out.println(genericMethod.genericMethod(12,34,56));
    }
}
