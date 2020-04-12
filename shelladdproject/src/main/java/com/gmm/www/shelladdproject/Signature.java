package com.gmm.www.shelladdproject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author:gmm
 * @date:2020/3/26
 * @类说明:
 */
public class Signature {

    public static void signature(File unsignedApk,File signedApk) throws IOException, InterruptedException {
        String cmd[] = {Dx.MAC_TERMINAL, "/C ","jarsigner",  "-sigalg", "MD5withRSA",
                "-digestalg", "SHA1",
                "-keystore", "C:/Users/allen/.android/debug.keystore",
                "-storepass", "android",
                "-keypass", "android",
                "-signedjar", signedApk.getAbsolutePath(),
                unsignedApk.getAbsolutePath(),
                "androiddebugkey"};

        Process process = Runtime.getRuntime().exec(cmd);
        System.out.println("start sign");

        try {
            int waitResult = process.waitFor();
            System.out.println("waitResult:" + waitResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }

        System.out.println("process.exitValue() " + process.exitValue() );
        if (process.exitValue() != 0) {
            InputStream is = process.getInputStream();
            int len;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != 0) {
                bos.write(buffer,0,len);
            }
            System.out.println(new String(bos.toByteArray(),"GBK"));
            throw new RuntimeException("签名执行失败");
        }

        System.out.println("finish signed");
        process.destroy();
    }
}
