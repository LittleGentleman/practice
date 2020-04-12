package com.example.gmm.mactestapp.thread;

/**
 * @author:gmm
 * @date:2020/2/4
 * @类说明: start和run方法的区别
 */
public class StartAndRun {

    public static class ThreadRun extends Thread {

        @Override
        public void run() {
            int i = 90;
            while (i > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("I am " + Thread.currentThread().getName()
                + " and now the i=" + i--);
            }
        }
    }

    public static void main(String[] args) {
        ThreadRun beCalled = new ThreadRun();
        beCalled.setName("beCalled");
//        beCalled.run();//run()方法就是一个普通的成员方法，没有启动新线程

        beCalled.start();//start()方法才是线程启动的方法
    }
}
