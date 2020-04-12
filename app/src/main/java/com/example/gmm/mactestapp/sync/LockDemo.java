package com.example.gmm.mactestapp.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:gmm
 * @date:2020/2/5
 * @类说明: 使用显示锁的范式
 */
public class LockDemo {

    private int count = 0;
    private Lock lock = new ReentrantLock();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private void incCount() {
        lock.lock();//获取锁，加锁
        try{
            count++;
        }finally {
            lock.unlock();//释放锁  一定要在finally中释放锁，防止因为抛异常导致锁不能释放
        }

    }

    public static void main(String[] args) throws InterruptedException {
        final LockDemo demo = new LockDemo();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        demo.incCount();
                    }

                }
            });
            thread.start();
        }
        Thread.sleep(50);

        System.out.println(demo.getCount());
    }
}
