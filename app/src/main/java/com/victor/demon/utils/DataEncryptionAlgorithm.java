package com.victor.demon.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import java.nio.charset.Charset;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <li>Java DES(Data Encryption Standard)加解密
 * <li>Java AES(Advanced Encryption Standard)加密
 * <p>Created by shixin on 2018/4/25.
 */
public class DataEncryptionAlgorithm {

    public static final String PASSWORD = "12345678";

    /**
     * 私钥。DES固定8bytes，AES16/24/32bytes
     */
    public static byte[] KEY_DES = PASSWORD.getBytes();

    /**
     * 初始化向量参数。DES固定8bytes，AES固定16bytes
     */
    public static byte[] IV_DES = {2,5,2,6,3,6,7,2};

    public static void test() {
        String str = "测试内容";

        byte[] result = encrypt(str.getBytes(), PASSWORD);
        Log.i("Victor", "encrypt "+new String(result));
        try{
            byte[] decryptResult = decrypt(result, PASSWORD);
            Log.i("Victor", "decrypt "+new String(decryptResult));
        }catch (Exception e) {
        }
    }

    public static byte[] encrypt(byte[] source, String password) {
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成密钥
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secureKey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, random);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(source);
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] source, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey secureKey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secureKey, random);
        // 真正开始解密操作
        return cipher.doFinal(source);
    }

    /**
     * DES加密算法
     */
    @SuppressLint("TrulyRandom")
    public static String encryptDES(String src) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec ks = new DESKeySpec(KEY_DES);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey sk = skf.generateSecret(ks);
        Cipher cip = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv2 = new IvParameterSpec(IV_DES);
        cip.init(Cipher.ENCRYPT_MODE, sk, iv2, sr);// IV的方式
        char[] dest2 = Base64.encode(
                cip.doFinal(src.getBytes(Charset.forName("UTF-8"))));
        return new String(dest2);
    }

    /**
     * DES解密算法
     */
    public static String decryptDES(String src) throws Exception {
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(KEY_DES);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv2 = new IvParameterSpec(IV_DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, iv2, random);
        byte[] st = cipher.doFinal(Base64.decode(src.toCharArray()));
        return new String(st, "UTF-8");
    }

    /**
     * AES加密算法
     * @param key aabbccddeeffgghh
     */
    public static String encryptAES(@NonNull String input, @NonNull String key) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return (new String(Base64.encode(crypted)));
    }
}
