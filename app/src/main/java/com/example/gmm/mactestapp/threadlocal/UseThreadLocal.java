package com.example.gmm.mactestapp.threadlocal;

/**
 * @author:gmm
 * @date:2020/2/5
 * @类说明:ThreadLocal 线程隔离
 */
public class UseThreadLocal {

    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){

        /**
         * 赋初始值
         * @return
         */
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    /**
     * 运行三个线程
     */
    public void startThreadArray() {
        Thread[] runs = new Thread[3];
        for (int i = 0; i < runs.length; i++) {
            runs[i] = new Thread(new TestTask(i));
        }
        for (int i = 0; i < runs.length; i++) {
            runs[i].start();
        }
    }

    public static class TestTask implements Runnable {
        int id;

        public TestTask(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":start");
            Integer s = threadLocal.get();
            s = s+id;
            threadLocal.set(s);
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
//            threadLocal.remove();
        }
    }

    public static void main(String[] args) {
        UseThreadLocal test = new UseThreadLocal();
        test.startThreadArray();
    }
}
