package com.bride.client;

import com.bride.baselib.codec.DataEncryptionAlgorithm;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import static com.bride.baselib.codec.DataEncryptionAlgorithm.DES_IV;
import static com.bride.baselib.codec.DataEncryptionAlgorithm.DES_KEY;

/**
 * DES对称加密算法加解密API
 * <p>Created by shixin on 2018/9/6.
 */
public class EncryptionClient {

    public static void main(String[] args) {
        String str = "测试内容";
        try{
            byte[] result = encrypt(str.getBytes(), DES_KEY);
            System.out.println("encrypt from "+str+" to "+new String(result, StandardCharsets.UTF_8));

            byte[] decryptResult = decrypt(result, DES_KEY);
            System.out.println("decrypt from "+new String(result, StandardCharsets.UTF_8)+" to "+new String(decryptResult, StandardCharsets.UTF_8));
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String cipherText = DataEncryptionAlgorithm.encryptDES(str, DES_KEY.getBytes(), DES_IV.getBytes());
            System.out.println("encrypt from "+str+" to "+cipherText);

            String plainText = DataEncryptionAlgorithm.decryptDES(cipherText, DES_KEY.getBytes(), DES_IV.getBytes());
            System.out.println("decrypt from "+cipherText+" to "+plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(byte[] source, String password) throws Exception {
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
}
