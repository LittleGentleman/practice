package com.gmm.www.javaio.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author:gmm
 * @date:2020/3/22
 * @类说明: 字符流
 */
public class BufferedWriterReaderTest {

    public static void main(String[] args) {

        File srcFile = new File("javaio/src/testtxt/BufferedReader.txt");
        File dstFile = new File("javaio/src/testtxt/BufferedWriter.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(srcFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(dstFile));

            char[] string = new char[1024];

            while (br.read(string) != -1) {
                bw.write(string);
            }

            br.close();
            bw.flush();//flush之前，数据都写入到了内存缓存中，flush之后才是写入硬盘文件里
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
