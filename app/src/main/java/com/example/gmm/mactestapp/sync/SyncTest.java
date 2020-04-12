package com.example.gmm.mactestapp.sync;

/**
 * @author:gmm
 * @date:2020/2/5
 * @类说明:
 */
public class SyncTest {
    private long count = 0;
    private Object obj = new Object();//作为一个锁

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    //count进行累加
    //synchronized 内置锁  不加锁的话，会存在同一时刻，多个线程同时访问，有了锁之后，多个线程需要顺序访问
    //对象锁，锁的是当前对象
    public synchronized void incCount() {
        count++;
    }

    //对象锁
    public void incCount2() {
        synchronized (obj) {
            count++;
        }

    }

    //对象锁
    public void incCount3() {
        synchronized (this) {
            count++;
        }

    }

    //类锁 锁的是类的class对象
    private static void incCount4() {

    }

    //线程
    private static class CountThread extends Thread {

        private SyncTest simpleOper;

        public CountThread(SyncTest simpleOper) {
            this.simpleOper = simpleOper;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {//count = count + 10000
                simpleOper.incCount2();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SyncTest simpleOper = new SyncTest();
        //启动两个线程
        CountThread thread1 = new CountThread(simpleOper);
        CountThread thread2 = new CountThread(simpleOper);
        thread1.start();
        thread2.start();
        Thread.sleep(50);
        System.out.println(simpleOper.count);
    }
}
