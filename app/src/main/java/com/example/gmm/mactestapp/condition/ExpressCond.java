package com.example.gmm.mactestapp.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:gmm
 * @date:2020/2/6
 * @类说明: Lock + condition  实现线程协作
 */
public class ExpressCond {
    public final static String CITY = "shanghai";
    private int km;//快递运输里程数
    private String site;//快递到达地点
    private Lock lock = new ReentrantLock();
    private Condition kmCond = lock.newCondition();
    private Condition siteCond = lock.newCondition();

    public ExpressCond() {
    }

    public ExpressCond(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /* 变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理*/
    public void changeKm() {
        lock.lock();//获取锁
        try {
            this.km = 101;
            kmCond.signalAll();//发通知
        } finally {
            lock.unlock();//释放锁
        }

    }

    /*变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理*/
    public void changeSite() {
        lock.lock();//获取锁
        try {
            this.site = "beijing";
            siteCond.signalAll();
        } finally {
            lock.unlock();//释放锁
        }
    }


    public void waitKm() {
        lock.lock();//获取锁
        try{
            while (this.km < 100) {
                try {
                    System.out.println("the km is " + this.km);
                    kmCond.await();
                    //等待状态，后面代码不会执行, 唤醒后从后面的代码开始执行
                    System.out.println("check km thread[" + Thread.currentThread().getName() +"] is be notified");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } finally {
            lock.unlock();
        }

        System.out.println("the km is " + this.km + ",I will change db.");
    }

    public void waitSite() {
        lock.lock();//获取锁
        try {
            while (CITY.equals(this.site)) {
                try {
                    siteCond.await();
                    System.out.println("check site thread[" + Thread.currentThread().getName() +"] is be notified");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }


        System.out.println("the site is " + this.site + ",I will call user.");
    }

}
