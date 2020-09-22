package com.gmm.www.handlerdemo;

/**
 * @author:gmm
 * @date:2020/5/10
 * @类说明:
 */
public class Looper {
    public MessageQueue mQueue;
    //ThreadLocal 隔离线程，保证线程和Looper是唯一的而且是一一对应的
    public static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();

    /**
     * 私有化构造器，不允许Looper通过构造器创建
     */
    private Looper() {
        mQueue = new MessageQueue();
    }

    /**
     * 获取当前线程的Looper
     * @return
     */
    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    /**
     * 这个方法才是创建Looper的方法
     */
    public static void prepare() {
        //获取当前线程的ThreadLocalMap，如果这个map总有这个ThreadLocal所对应的的Entry以及Looper，说明这个线程已经有Looper了，不需要再创建了
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        //已当前ThreadLocal为key，新的Looper为value，包装成Entry，并根据ThreadLocal的hashcode计算index，将Entry添加到map中index所对应的位置上
        sThreadLocal.set(new Looper());
    }

    /**
     * 轮询消息队列
     */
    public static void loop() {
        //保证使用的是当前线程的Looper
        final Looper me = myLooper();
        //保证轮询的是当前Looper的消息队列
        final MessageQueue queue = me.mQueue;
        //死循环
        for (;;) {
            //在系统Looper中，如果队列中没有消息或者队列已满，那么队列会阻塞，等到调用消息队列的enqueueMessage或者next方法，队列会停止阻塞
            //系统的MessageQueue的next方法中，先判断队列中第一个Message的时间是否已到，如果时间没到或者队列中没有消息，会调用native层来释放时间片，这就是为什么主线程不会因为消息队列的等待而出现ANR
            Message msg = queue.next();
            if (msg == null)
                return;

            msg.target.dispatchMessage(msg);
        }
    }
}
