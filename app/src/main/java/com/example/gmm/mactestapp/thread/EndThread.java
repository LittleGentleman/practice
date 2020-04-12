package com.example.gmm.mactestapp.thread;

/**
 * @author:gmm
 * @date:2020/2/4
 * @类说明: 如何安全的中断线程
 */
public class EndThread {

    private static class UseThread extends Thread {

        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " interrupt flag = " +  isInterrupted());
            while (!isInterrupted()) {
                System.out.println(threadName + " is running");
            }

            System.out.println(threadName + " interrupt flag = " +  isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread endThread = new UseThread("endThread");
        endThread.start();
//        endThread.start(); //同一线程 不能多次调用start()方法
        Thread.sleep(1);
        endThread.interrupt();
    }
}
