package com.gmm.www.librarytest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author:gmm
 * @date:2020/3/26
 * @类说明:
 */
public class Zip {

    /**
     *
     * @param zip  待解压的文件
     * @param dir  解压后的文件存放位置
     */
    public static void unZip(File zip,File dir) {
        try {
            dir.delete();
            ZipFile zipFile = new ZipFile(zip);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                String name = zipEntry.getName();
                if (name.equals("META-INF/CERT.RSA") || name.equals("META-INF/CERT.SF") || name
                        .equals("META-INF/MANIFEST.MF")) {
                    continue;
                }
                if (!zipEntry.isDirectory()) {
                    File file = new File(dir,name);
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();

                    FileOutputStream fos = new FileOutputStream(file);
                    InputStream is = zipFile.getInputStream(zipEntry);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer,0,len);
                    }
                    is.close();
                    fos.close();
                }
            }
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件
     * @param dir  待压缩的源文件
     * @param zip  压缩后生成的压缩文件
     */
    public static void zip(File dir,File zip) throws Exception {
        zip.delete();
        //对输出的文件做CRC32校验
        CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(zip),new CRC32());
        ZipOutputStream zos = new ZipOutputStream(cos);
        compress(dir,zos,"");
        zos.flush();
        zos.close();
    }

    /**
     * 真正的压缩环节
     * @param srcFile  带压缩文件
     * @param zos    校验过的zip文件，但是zip中还没有内容
     * @param basePath
     */
    private static void compress(File srcFile,ZipOutputStream zos,String basePath) throws IOException {
        if (srcFile.isDirectory()) {
            compressDir(srcFile,zos,basePath);
        } else {
            compressFile(srcFile,zos,basePath);
        }
    }

    //如果是文件夹
    private static void compressDir(File dir,ZipOutputStream zos,String basePath) throws IOException {
        File[] files = dir.listFiles();
        //构建空目录
        if (files.length < 1) {
            ZipEntry entry = new ZipEntry(basePath + dir.getName() + File.separator);
            zos.putNextEntry(entry);
            zos.closeEntry();
        }
        for (File file :
                files) {
            //递归压缩
            compress(file,zos,basePath + dir.getName() + File.separator);
        }
    }

    //如果是文件
    private static void compressFile(File file,ZipOutputStream zos,String dir) throws IOException {
        String dirName = dir + file.getName();

        String[] dirNameNew = dirName.split(File.pathSeparator);

        StringBuffer sb = new StringBuffer();

        if (dirNameNew.length > 1) {
            for (int i = 1; i < dirNameNew.length; i++) {
                sb.append(File.separator);
                sb.append(dirNameNew[i]);
            }
        } else {
            sb.append(File.separator);
        }

        ZipEntry entry = new ZipEntry(sb.toString().substring(1));
        zos.putNextEntry(entry);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        int length;
        byte[] buffer = new byte[1024];
        while ((length = bis.read(buffer,0,1024)) != -1) {
            zos.write(buffer,0,length);
        }

        bis.close();
        zos.closeEntry();
    }
}
