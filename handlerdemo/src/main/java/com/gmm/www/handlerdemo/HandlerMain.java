package com.gmm.www.handlerdemo;

import java.util.UUID;

/**
 * @author:gmm
 * @date:2020/5/10
 * @类说明:
 */
public class HandlerMain {

    public static void main(String[] args) {

        //为当前线程绑定唯一Looper  先创建Looper
        Looper.prepare();

        //在当前线程创建Handler
        final Handler handler = new Handler() {
            /**
             * 处理dispatch过来的消息
             * @param msg
             */
            @Override
            public void handlerMessage(Message msg) {
                System.out.println("Thread Id: " + Thread.currentThread().getName() + " received msg: " + msg.toString());
            }
        };

        new Thread(new Runnable() {
            int count = 1000;
            @Override
            public void run() {
                while (count > 0) {
                    Message msg = new Message(UUID.randomUUID().toString());
                    System.out.println(Thread.currentThread().toString() + " send message: " + msg.toString() + " count: " + count);
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

//        for (int i = 0; i < 10; i++) {
//            new Thread() {
//                public void run() {
//                    while (true) {
//                        String string;
//                        synchronized (UUID.class) {
//                            string = UUID.randomUUID().toString();
//                        }
//                        Message msg = new Message(string);
//                        System.out.println(Thread.currentThread().getName() + " send Msg: " + msg.toString());
//                        handler.sendMessage(msg);
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//
//                ;
//            }.start();
//        }

        Looper.loop();

    }
}
