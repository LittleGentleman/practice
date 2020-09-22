package com.gmm.www.socketdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author:gmm
 * @date:2020/5/14
 * @类说明:
 */
public class SocketServer {

    public static void main(String[] args) throws IOException {

        //服务端Socket
        ServerSocket serverSocket = new ServerSocket(10079);
        System.out.println("调用accept之前");
        //获取Socket，等待客户端连接
        Socket socket = serverSocket.accept();
        System.out.println("调用accept之后");

        //获取输入流
        InputStream is = socket.getInputStream();
        //转成字符流
        InputStreamReader isr = new InputStreamReader(is);
        //转成缓冲字符流
        BufferedReader br = new BufferedReader(isr);

        String line = null;

        while ((line = br.readLine()) != null) {
            System.out.println("客户端发送过来的信息：" + line);
        }

//        br.close();
//        isr.close();
//        is.close();
        socket.shutdownInput();//关闭输入流
        socket.close();

    }
}
