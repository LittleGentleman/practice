package com.gmm.www.lambdademo;

/**
 * @author:gmm
 * @date:2020/3/3
 * @类说明:
 */
public class Main {

    public static void main(String[] args) {

        //匿名的内部类 java
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello Java");
            }
        };

        //lambda
        Runnable run2 = () -> {
            System.out.println("Hello Lambda");
        };

        new Thread(run1).start();
        new Thread(run2).start();

//        LambdaInterface1 lambdaInterface1 = new LambdaInterface1() {
//            @Override
//            public void test() {
//
//            }
//        };

        //java7 函数（方法）是一等公民，可以当做是普通的变量、参数
        LambdaInterface1 lambdaInterface1 = () -> System.out.println("①无参无返回值");
        lambdaInterface1.test();

        LambdaInterface2 lambdaInterface2 = s -> System.out.println(s);
        lambdaInterface2.test("②一个参数无返回值");

        LambdaInterface3 lambdaInterface3 = (s,i) -> {
            i = i+10;
            s = s + i;
            System.out.println(s);
        };
        lambdaInterface3.test("③多个参数无返回值",2010);

        LambdaInterface4 lambdaInterface4 = (s1, s2) -> s1 + s2;
        System.out.println(lambdaInterface4.test("④多个参数","有返回值"));

        // :: Method reference （方法引用）

    }

    @FunctionalInterface
    interface LambdaInterface1 {
        void test();
    }

    @FunctionalInterface
    interface LambdaInterface2 {
        void test(String s);
    }

    @FunctionalInterface
    interface LambdaInterface3 {
        void test(String s, int i);
    }

    @FunctionalInterface
    interface LambdaInterface4 {
        String test(String s1,String s2);
    }

    @FunctionalInterface
    interface LambdaInterface5 {
        String test1(String s1,String s2);
    }
}
