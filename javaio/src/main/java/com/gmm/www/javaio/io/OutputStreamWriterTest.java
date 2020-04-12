package com.gmm.www.javaio.io;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author:gmm
 * @date:2020/3/22
 * @类说明:字节——>字符
 */
public class OutputStreamWriterTest {

    private final static String STRING = "I like CAT";

    public static void main(String[] args) {
        testOutputStreamWriter();
    }

    private static void testOutputStreamWriter() {


        try {
            File file = new File("javaio/src/testtxt/OutputStreamWriter.txt");
            //true, 设置内容可以追加
            FileOutputStream fos = new FileOutputStream(file,true);
            OutputStreamWriter osWriter = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osWriter);
            bw.write(STRING);
            bw.newLine();
            bw.flush();

            System.out.println("osWriter encoding :" + osWriter.getEncoding());

            OutputStreamWriter oswGBk = new OutputStreamWriter(fos,"GBK");
            BufferedWriter bwGBK = new BufferedWriter(oswGBk);

            bwGBK.write(STRING + " GBK");
            bwGBK.newLine();
            bwGBK.flush();
            System.out.println("oswGBk encoding:" + oswGBk.getEncoding());

            bw.close();
            bwGBK.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
