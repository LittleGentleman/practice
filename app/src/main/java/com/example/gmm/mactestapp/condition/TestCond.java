package com.example.gmm.mactestapp.condition;

/**
 * @author:gmm
 * @date:2020/2/6
 * @类说明:
 */
public class TestCond {

    private static ExpressCond express = new ExpressCond(0,ExpressCond.CITY);

    private static class CheckKm extends Thread {
        @Override
        public void run() {
            express.waitKm();
        }
    }

    private static class CheckSite extends Thread {
        @Override
        public void run() {
            express.waitSite();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new CheckKm().start();
        }
        for (int i = 0; i < 3; i++) {
            new CheckSite().start();
        }

        Thread.sleep(1000);
        express.changeKm();
    }
}
