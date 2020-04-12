package com.example.gmm.mactestapp.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @author:gmm
 * @date:2020/2/6
 * @类说明: 线程池的实现
 */
public class MyThreadPool {
    //线程池中默认线程的个数为5
    public static final int WORK_NUM = 5;
    //队列默认任务个数为100
    public static final int TASK_NUM = 100;

    //工作线程组
    private WorkThread[] workThreads;

    //任务队列
    private BlockingQueue<Runnable> taskQueue;
    private int work_num;//用户在构造这个池，希望启动的线程数

    public MyThreadPool() {
        this(WORK_NUM,TASK_NUM);
    }

    public MyThreadPool(int work_num,int task_num){
        if (work_num<=0) work_num = WORK_NUM;
        if (task_num<=0) task_num = TASK_NUM;
        this.work_num = work_num;
        workThreads = new WorkThread[work_num];
        taskQueue = new ArrayBlockingQueue<>(task_num);
        for (int i = 0; i < work_num; i++) {
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
//      int cpuCount = Runtime.getRuntime().availableProcessors();//获取CPU的个数（核心数，可同时执行的线程数）
    }

    //执行任务，其实只是把任务加入任务队列，什么时候执行由线程池管理器决定
    public void execute(Runnable task) {
        try {
            taskQueue.put(task);//阻塞，当队列满了，新添加的任务会阻塞等待，等队列不满的时候，继续添加到队列里
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //销毁线程池，该方法保证在所有任务都完成的情况下才销毁所有线程，否则等待任务完成才销毁
    public void destroy() {
        //工作线程停止工作，且置为null
        System.out.println("read close pool.....");
        for (int i = 0; i < work_num; i++) {
            workThreads[i].stopWork();
            workThreads[i] = null;//help gc
        }
        taskQueue.clear();//清空任务队列
    }

    /**
     * 返回线程池信息：工作线程个数和已完成任务个数
     * @return
     */
    @Override
    public String toString() {
        return "WorkThread number:" + work_num + " wait task number" + taskQueue.size();
    }

    /*
        工作线程
     */
    public class WorkThread extends Thread {
        @Override
        public void run() {
            Runnable r = null;
            try {
                while (!isInterrupted()) {
//                    System.out.println("running:" + Thread.currentThread().getName());
                    r = taskQueue.take();//阻塞，当队列为空，线程阻塞等待，当队列内有任务后，取出任务
                    if (r != null) {
                        System.out.println(getId() + " ready exec:" + r);
                        r.run();
                    }
                }
            } catch (Exception e) {

            }
        }

        public void stopWork() {
            interrupt();
        }
    }
}
