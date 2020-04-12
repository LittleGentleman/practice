package com.example.gmm.mactestapp.sync;

import javax.xml.transform.Source;

/**
 * @author:gmm
 * @date:2020/2/5
 * @类说明:快递实体类
 */
public class Express {
    public final static String CITY = "shanghai";
    private int km;//快递运输里程数
    private String site;//快递到达地点

    public Express() {
    }

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /* 变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理*/
    public synchronized void changeKm() {//锁当前对象
        this.km = 101;
        notifyAll();
    }

    /*变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理*/
    public synchronized void changeSite() {
        this.site = "beijing";
        notifyAll();
    }

    //synchronized 获取锁
    public synchronized void waitKm() {

        while (this.km < 100) {
            try {
                System.out.println("the km is " + this.km);
                wait();//等待状态，后面代码不会执行, 唤醒后从后面的代码开始执行
                System.out.println("check km thread[" + Thread.currentThread().getName() +"] is be notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the km is " + this.km + ",I will change db.");
    }

    public synchronized void waitSite() {

        while (CITY.equals(this.site)) {
            try {
                wait();//处于等待状态，下面的代码不会执行，唤醒后，继续执行后面的代码
                System.out.println("check site thread[" + Thread.currentThread().getName() +"] is be notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("the site is " + this.site + ",I will call user.");
    }
}
