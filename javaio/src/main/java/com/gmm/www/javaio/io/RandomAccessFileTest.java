package com.gmm.www.javaio.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author:gmm
 * @date:2020/3/22
 * @类说明:
 */
public class RandomAccessFileTest {

    private static final File FILE = new File("javaio/src/testtxt/raf.txt");

    public static void main(String[] args) throws IOException {
        testRandomAccessFileWriter();
        testRandomAccessFileRead();
    }

    private static void testRandomAccessFileWriter() throws IOException {
        //要先将已有文件删除，避免干扰
        if (FILE.exists()) {
            FILE.delete();
        }

        RandomAccessFile rafWriter = new RandomAccessFile(FILE,"rw");

        //不会改变文件大小、但是他会将下一个字符的写入位置标识为10000
        //也就是说此后只要写入内容，就是从10001开始存
        rafWriter.seek(10000);
        printFileLength(rafWriter);//result 0

        //会改变文件大小，只是把文件的size改变，
        //并没有改变下一个要写入的内容的位置，
        rafWriter.setLength(10000);//如果内容超过这个值，文件会根据写入内容的大小自动变大size
        printFileLength(rafWriter);//result 10000

        //每个汉字占3个字节，写入字符串的时候会有一个记录写入字符串长度的两个字节
        rafWriter.writeUTF("享学课堂");//一共4*3+2 = 14个字节
        printFileLength(rafWriter);//result 10000+14 = 10014

        //每个字符占两个字节
        rafWriter.writeChar('a');
        rafWriter.writeChars("abcde");
        printFileLength(rafWriter);// (1+5)*2 = 12  result 10014+12=10026

        //再从"文件指针"为5000的地方插一个长度为100，内容全是'a'的字符数组
        //这里file长度依然是10026，因为他是从"文件指针"为5000的地方覆盖后面
        //的200个字节，下标并没有超过文件长度
        rafWriter.seek(5000);
        char[] cbuf = new char[100];
        for (int i = 0; i < cbuf.length; i++) {
            cbuf[i] = 'a';
            rafWriter.writeChar(cbuf[i]);
        }
        printFileLength(rafWriter);//result = 10026


    }

    private static void testRandomAccessFileRead() throws IOException{

        RandomAccessFile rafReader = new RandomAccessFile(FILE,"rw");

        rafReader.seek(10000);
        System.out.println(rafReader.readUTF());

        System.out.println(rafReader.readChar());

        byte[] buf01 = new byte[10];
        rafReader.read(buf01);
        System.out.println(new String(buf01));

        rafReader.seek(5000);
        byte[] buf02 = new byte[200];
        rafReader.read(buf02);
        System.out.println(new String(buf02));
    }

    private static void printFileLength(RandomAccessFile rsfWriter)
            throws IOException {
        System.out.println("file length: " + rsfWriter.length() + "  file pointer: " + rsfWriter.getFilePointer());
    }
}
