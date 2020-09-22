package com.gmm.www.handlerdemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author:gmm
 * @date:2020/5/10
 * @类说明:
 */
public class MessageQueue {
    //阻塞队列
    BlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);

    /**
     * 生产者：发送消息
     * @param msg
     */
    public void enqueueMessage(Message msg) {
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费者
     *
     * @return
     */
    public Message next() {
        Message msg = null;
        try {
           msg = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
