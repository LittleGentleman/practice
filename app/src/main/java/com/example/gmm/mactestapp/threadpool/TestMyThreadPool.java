package com.example.gmm.mactestapp.threadpool;

import java.util.Random;

/**
 * @author:gmm
 * @date:2020/2/6
 * @类说明:
 */
public class TestMyThreadPool {

    public static void main(String[] args) throws InterruptedException {
        //创建三个线程的线程池
        MyThreadPool pool = new MyThreadPool(3,0);
        pool.execute(new MyTask("taskA"));
        pool.execute(new MyTask("taskB"));
        pool.execute(new MyTask("taskC"));
        pool.execute(new MyTask("taskD"));
        pool.execute(new MyTask("taskE"));
        System.out.println(pool);
        Thread.sleep(10000);
        pool.destroy();
        System.out.println(pool);

    }

    static class MyTask implements Runnable {
        String name;
        Random r = new Random();

        public MyTask(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {//执行任务
            try {
                Thread.sleep(r.nextInt(1000) + 2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getId() + " sleep interrupt: " +
                Thread.currentThread().isInterrupted());
            }
            System.out.println("任务" + name + " 完成");
        }
    }
}
