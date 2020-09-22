package com.gmm.www.socketdemo;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author:gmm
 * @date:2020/5/14
 * @类说明:
 */
public class ChatSocketServer {
    private ServerSocket serverSocket = null;
    private static final int PORT = 10087;
    private List<Socket> mSockets = new ArrayList<>();
    private ExecutorService mExec = null;

    public ChatSocketServer() {
        //开启服务
        System.out.println("服务器运行中");
        try {
            serverSocket = new ServerSocket(PORT);
            mExec = Executors.newCachedThreadPool();//创建一个线程池
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("ip:" + inetAddress.getHostAddress());

            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                mSockets.add(socket);

                mExec.execute(new SocketRunnable(socket));//启动一个线程，在子线程中进行socket通信
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatSocketServer();
    }

    class SocketRunnable implements Runnable {

        private Socket socket = null;

        private BufferedReader br = null;
        private String msg = "";

        public SocketRunnable(Socket socket) {
            this.socket = socket;

            try {
                //获取socket的输入流
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.sendMsg();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMsg() {
            int num = mSockets.size();
            OutputStream os = null;

            try {
                os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                pw.write("你好 你是第" + num + "个客户");
                pw.flush();

                //不要关闭socket

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if ((msg = br.readLine()) != null) {
                        System.out.println("客户端说：" + msg);

                        if ("bye".equals(msg)) {
                            socket.close();//关闭socket
                            break;//终止死循环
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
