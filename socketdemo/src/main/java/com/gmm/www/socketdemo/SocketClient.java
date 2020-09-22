package com.gmm.www.socketdemo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author:gmm
 * @date:2020/5/14
 * @类说明:
 */
public class SocketClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",10079);//127.0.0.1  本地ip

        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);

        pw.write("Hello SocketServer");//写入了缓存
        pw.flush();//这才会真正输出

//        pw.close();
//        os.close();
        socket.shutdownOutput();//关闭输出流
        socket.close();

    }
}
