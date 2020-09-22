package com.example.gmm.mactestapp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author:gmm
 * @date:2020/7/27
 * @类说明:
 */
public class CountDownLatchTest implements Runnable {

    final AtomicInteger number = new AtomicInteger();
    volatile boolean bool = false;

    @Override
    public void run() {
        System.out.println(number.getAndIncrement());
        synchronized (this) {

            try {
                if (!bool) {
                    System.out.println(bool);
                    bool = true;
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("并发数量为" + number.intValue());
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        CountDownLatchTest run = new CountDownLatchTest();
        for (int i = 0; i < 10; i++) {
            pool.execute(run);
        }
    }
}
