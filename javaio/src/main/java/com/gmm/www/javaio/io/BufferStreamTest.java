package com.gmm.www.javaio.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author:gmm
 * @date:2020/3/22
 * @类说明:
 */
public class BufferStreamTest {
    private static final byte[] byteArray = {
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A
    };


    public static void main(String[] args) {
        bufferedOutPutStream();
        bufferedInputStream();
    }

    private static void bufferedOutPutStream() {

        try {
            //装饰器模式
            File file = new File("javaio/src/testtxt/BufferedStreamTest.txt");
            FileOutputStream fos = new FileOutputStream(file);//装饰器模式中具体的被装饰的原始对象
            BufferedOutputStream bos = new BufferedOutputStream(fos);//装饰器模式中的具体的装饰器
            bos.write(byteArray[0]);
            bos.write(byteArray, 1, byteArray.length - 1);
            bos.flush();//flush之前，写入的内容都在缓存内存中，flush之后，才是真正的写入硬盘中
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void bufferedInputStream() {
        try {
            File file = new File("javaio/src/testtxt/BufferedStreamTest.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

//            bis.mark(6666);
//            bis.skip(10);

            byte[] b = new byte[1024];
            int n1 = bis.read(b,0,b.length);
            System.out.println("n1: " + n1);
            printByteValue(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void printByteValue(byte[] buf) {
        for (byte b : buf) {
            if (b != 0) {
                System.out.print(byteToString(b));
            }
        }
    }

    private static String byteToString(byte b) {
        byte[] byteArray = {b};
        return new String(byteArray);
    }

}
