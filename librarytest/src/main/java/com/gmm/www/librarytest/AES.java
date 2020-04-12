package com.gmm.www.librarytest;

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
