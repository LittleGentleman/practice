package com.gmm.www.shelladdproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author:gmm
 * @date:2020/3/25
 * @类说明:
 */
public class MyMain {

    private static String ROOTPATH = "shelladdproject/src/main/java/com/gmm/www/shelladdproject/";

    public static void main(String[] args) throws Exception {

        //创建存放 apk 解压后文件的目录
        File tempFileApk = new File(ROOTPATH + "source/apk/temp");
        if (tempFileApk.exists()) {
            File[] files = tempFileApk.listFiles();
            for (File file :
                    files) {
                file.delete();
            }
        }

        //创建存放 aar 解压后文件的目录
        File tempFileAar = new File(ROOTPATH +"source/aar/temp");
        if (tempFileAar.exists()) {
            File[] files = tempFileAar.listFiles();
            for (File file :
                    files) {
                file.delete();
            }
        }

        /**
         * 第一步： 处理原始apk 解压apk，得到dex文件进行加密
         */
        AES.init(AES.DEFAULT_PWD);
        File apkFile = new File(ROOTPATH + "source/apk/app-debug.apk");
        File unzipApkFile = new File(apkFile.getParent() + File.separator + "temp");
        if (!unzipApkFile.exists()){
            unzipApkFile.mkdir();
        }
        //解压apk到指定目录下，然后过滤解压后的文件获得dex文件，给dex文件进行加密，此操作之后，所有的dex文件都是加密后的dex
        File mainDexFile = AES.encryptAPKFile(apkFile, unzipApkFile);//返回的是主包dex文件
        if (unzipApkFile.isDirectory()) {
            File[] listFiles = unzipApkFile.listFiles();
            for (File file:
                 listFiles) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".dex")) {
                        String name = file.getName();
                        System.out.println("dex file origin name:" + name);
                        int index = name.indexOf(".dex");
                        String newName = file.getParent() + File.separator + name.substring(0,index) + "_" + ".dex";
                        System.out.println("dex file new name:" + newName);
                        file.renameTo(new File(newName));//给dex文件重新命名，防止与壳dex文件重名
                    }
                }
            }
        }

        /**
         * 第二步： library生成的aar包中有classes.jar文件，所以需要处理aar文件，并获得它的classes.jar文件
         * 然后处理成classes.dex 作为 壳dex
         */
        File aarFile = new File(ROOTPATH + "source/aar/librarytest-debug.aar");

        //将aar文件里的classes.jar转换成classes.dex
        File dexFile = Dx.jar2Dex(aarFile);

        //将这个dex文件 作为 apk中的主包 classes.dex，也就是将这个classes.dex写入到 apk 解压后的文件目录里
        //首先在apk解压后的目录里创建classes.dex文件（此时apk解压后的文件里只有classes_.dex文件）
        File tempMainDex = new File(unzipApkFile + File.separator + "classes.dex");
        if (!tempMainDex.exists()) {
            tempMainDex.createNewFile();
        }
        //文件创建后，将aar里通过classes.jar转换过来的classes.dex文件，写入到 刚才在apk 解压目录创建的classes.dex文件
        FileOutputStream fos = new FileOutputStream(tempMainDex);
        byte[] dexBytes = Utils.getBytes(dexFile);
        fos.write(dexBytes);
        fos.flush();
        fos.close();

        /**
         * 第三步：打包签名
         * 此时apk解压目录里 存在 classes_.dex 和 classed.dex
         * 其中classes_.dex是加密过的源dex文件，classes.dex是library生成的aar中的classes.jar转换的classed.dex,作为壳dex
         * 首先：将这个解压目录重新压缩打包，压缩打包成新的apk文件
         * 然后：对这个新的apk进行签名
         */
        File unsignedApk = new File(ROOTPATH + "result/apk-unsigned.apk");
        unsignedApk.getParentFile().mkdirs();

        //把解压文件 压缩成 apk
        Zip.zip(unzipApkFile,unsignedApk);
        //创建签名 apk
        File signedApk = new File(ROOTPATH + "result/apk-signed.apk");
        Signature.signature(unsignedApk,signedApk);
    }
}
