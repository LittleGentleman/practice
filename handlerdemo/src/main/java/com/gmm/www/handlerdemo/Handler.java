package com.gmm.www.handlerdemo;

/**
 * @author:gmm
 * @date:2020/5/10
 * @类说明:
 */
public class Handler {
    //当前handler所对应的线程中的Looper（唯一）
    final Looper mLooper;
    //当前handler所对应的的线程的Looper中的MessageQueue （唯一）
    final MessageQueue mQueue;

    public Handler() {
        //获取线程唯一Looper
        mLooper = Looper.myLooper();
        //获取Looper唯一MessageQueue
        mQueue = mLooper.mQueue;
    }


    public void sendMessage(Message msg) {
        //将消息添加到messageQueue队列中
        enqueueMessage(msg);
    }

    /**
     * 消息添加到队列中
     * @param msg
     */
    private void enqueueMessage(Message msg) {
        //将消息与Handler绑定，这样就知道这条Message由哪个Handler处理
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }

    /**
     * 最少知识原则
     * 消费者：分发Looper的loop()方法中从消息队列取出的消息
     * @param msg
     */
    public void dispatchMessage(Message msg) {
        handlerMessage(msg);
    }

    public void handlerMessage(Message msg) {

    }
}
