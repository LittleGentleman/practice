package com.gmm.www.shelladdproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author:gmm
 * @date:2020/3/25
 * @类说明:
 */
public class AES {
    public static final String DEFAULT_PWD = "abcdefghijklmnop";

    private static final String algorithmStr = "AES/ECB/PKCS5Padding";

    private static Cipher encryptCipher;
    private static Cipher decryptCipher;

    public static void init(String password) {

        //生成一个实现指定转换的Cipher 对象
        try {
            encryptCipher = Cipher.getInstance(algorithmStr);
            decryptCipher = Cipher.getInstance(algorithmStr);
            byte[] keyStr = password.getBytes();
            SecretKeySpec key = new SecretKeySpec(keyStr,"AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE,key);
            decryptCipher.init(Cipher.DECRYPT_MODE,key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param srcAPKFile 源文件所在位置
     * @param dstAPKFile  目标文件
     * @return 返回 主包dex文件
     */
    public static File encryptAPKFile(File srcAPKFile,File dstAPKFile) throws Exception {
        if (srcAPKFile == null) {
            System.out.println("encryptAPKFile :srcAPKfile null");
            return null;
        }

        //将apk 进行解压 ，解压文件写进dstAPKFile目录下
        Zip.unZip(srcAPKFile,dstAPKFile);

        //将解压的dex文件 进行加密
        //获得所有的dex （需要处理分包情况）
        File[] dexFiles = dstAPKFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".dex");
            }
        });

        File mainDexFile = null;
        byte[] mainDexData = null;

        for (File dexFile :
                dexFiles) {
            //读数据 将dex文件 转换成 byte[]
            byte[] buffer = Utils.getBytes(dexFile);
            //加密 dex文件的 字节数据
            byte[] encryptBytes = AES.encrypt(buffer);

            if (dexFile.getName().endsWith("classes.dex")) {//主包
                mainDexData = encryptBytes;
                mainDexFile = dexFile;
            }

            //写数据，将加密后的byte数据 写入到原来的文件里，达到文件加密效果
            FileOutputStream fos = new FileOutputStream(dexFile);
            fos.write(encryptBytes);
            fos.flush();//触发文件写操作
            fos.close();
        }
        //主包dex文件
        return mainDexFile;
    }

    public static byte[] encrypt(byte[] data) {
        try {
            byte[] result = encryptCipher.doFinal(data);
            return result;
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
