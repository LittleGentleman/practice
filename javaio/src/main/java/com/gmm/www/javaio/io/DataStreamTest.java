package com.gmm.www.javaio.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author:gmm
 * @date:2020/3/22
 * @???:字节流
 */
public class DataStreamTest {

    public static void main(String[] args) {
        testDataOutPutStream();
        testDataInputStreamI();
    }

    private static void testDataOutPutStream() {
        try {
            //?????
            File file = new File("javaio/src/testtxt/DataStreamTest.txt");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            DataOutputStream dos = new DataOutputStream(bos);

            dos.writeBoolean(true);
            dos.writeByte((byte)0x41);
            dos.writeChar((char)0x4343);
            dos.writeShort((short)0x4445);
            dos.writeInt(0x12345678);
            dos.writeLong(0x987654321L);

            dos.writeUTF("abcdefghijklmnopqrstuvwxyz");
            dos.writeLong(0x023433L);
            dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testDataInputStreamI() {
        try {
            File file = new File("javaio/src/testtxt/DataStreamTest.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            System.out.println(dis.readBoolean());
            System.out.println(byteToHexString(dis.readByte()));
            System.out.println(charToHexString(dis.readChar()));
            System.out.println(shortToHexString(dis.readShort()));
            System.out.println(Integer.toHexString(dis.readInt()));
            System.out.println(Long.toHexString(dis.readLong()));
            System.out.println(dis.readUTF());
            System.out.println(Long.toHexString(dis.readLong()));

            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 打印byte对应的16进制的字符串
    private static String byteToHexString(byte val) {
        return Integer.toHexString(val & 0xff);
    }

    // 打印char对应的16进制的字符串
    private static String charToHexString(char val) {
        return Integer.toHexString(val);
    }

    // 打印short对应的16进制的字符串
    private static String shortToHexString(short val) {
        return Integer.toHexString(val & 0xffff);
    }
}
