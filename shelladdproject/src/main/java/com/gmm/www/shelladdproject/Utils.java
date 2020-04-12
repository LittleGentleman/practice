package com.gmm.www.shelladdproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * @author:gmm
 * @date:2020/3/26
 * @类说明:
 */
public class Utils {

    public static byte[] getBytes(File dexFile) throws Exception {
        //创建可读的RandomAccessFile文件
        RandomAccessFile raf = new RandomAccessFile(dexFile,"r");
        byte[] buffer = new byte[(int) raf.length()];
        //通过RandomAccessFile 的 readFully方法，可以将文件的所有数据存入到字节数组里
        raf.readFully(buffer);
        raf.close();
        return buffer;
    }
}
