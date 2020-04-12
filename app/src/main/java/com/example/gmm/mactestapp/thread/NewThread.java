package com.example.gmm.mactestapp.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author:gmm
 * @date:2020/2/4
 * @类说明: 如何新建线程  Thread是线程  Runnable和Callable可以理解成为任务，Thread执行任务
 */
public class NewThread {
    //扩展自Thread类
    private static class UseThread extends Thread {
        @Override
        public void run() {
            super.run();
            //do work
            System.out.println("I am extends Thread");
        }
    }

    //实现Runnable接口
    private static class UseRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("I am implements Runnable");
        }
    }

    //实现Callable接口，允许有返回值
    private static class UseCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("I am implements Callable");
            return "Callable";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        UseThread useThread = new UseThread();
        useThread.start();

        UseRunnable useRunnable = new UseRunnable();
        new Thread(useRunnable).start();

        UseCallable useCallable = new UseCallable();
        FutureTask<String> futureTask = new FutureTask<>(useCallable);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());//futureTask.get() 可以获取到线程执行后的返回值
    }
}
