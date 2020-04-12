package com.gmm.www.shelladdproject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author:gmm
 * @date:2020/3/26
 * @类说明:
 */
public class Dx {

    static String MAC_TERMINAL = "/Applications/Utilities/Terminal.app/Contents/MacOS/Terminal";

    public static File jar2Dex(File aarFile) throws IOException, InterruptedException {
        //aar文件解压后存放的目录
        File unzipFile = new File(aarFile.getParent() + File.separator + "temp");
        //解压aar文件，解压后放在指定目录下
        Zip.unZip(aarFile,unzipFile);
        //过滤找到对象的unzipFile 下的  classes.jar文件
        File[] jarFiles = unzipFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        });
        if (jarFiles == null || jarFiles.length <= 0) {
            throw new RuntimeException("the aar is invalidate");
        }
        File classesJar = jarFiles[0];
        //classes.jar 变成 classes.dex
        File classesDex = new File(classesJar.getParentFile(),"classes.dex");

        //我们要将jar转变成 dex，需要使用android tools 里面的dx.bat
        //使用java 调用  命令
        Dx.dxCommand(classesDex,classesJar);
        return classesDex;
    }

    public static void dxCommand(File classesDex,File classesJar) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        Process process = runtime.exec(new String[]{MAC_TERMINAL,"/C dx --dex --output=" + classesDex.getAbsolutePath()
                + " " + classesJar.getAbsolutePath()});

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }

        if (process.exitValue() != 0) {
            InputStream is = process.getInputStream();
            int len;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer,0,len);
            }
            System.out.println(new String(bos.toByteArray(),"GBK"));
            throw new RuntimeException("dx run failed");
        }
        process.destroy();
    }
}
