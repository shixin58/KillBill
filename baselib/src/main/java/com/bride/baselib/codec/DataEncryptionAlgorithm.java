package com.bride.baselib.codec;

import android.annotation.SuppressLint;

import java.nio.charset.Charset;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密symmetric cryptography：加解密使用相同密钥。非对称加密asymmetric cryptography：用公钥publicKey加密、私钥privateKey解密，或私钥加密，公钥解密。
 * 常用对称加密算法有：DES(Data Encryption Standard)加解密，AES(Advanced Encryption Standard)加密。常用非对称加密算法有：RSA, 椭圆曲线加密Elliptic Curve Cryptography。
 * <p>Created by shixin on 2018/4/25.
 */
public class DataEncryptionAlgorithm {

    // 私钥。DES固定8bytes，AES16/24/32bytes
    public static String DES_KEY = "mrXn5pHX";

    // 初始化向量参数。DES固定8bytes，AES固定16bytes
    public static String DES_IV = "k9589Iau";

    /**
     * DES加密算法
     * <p>四种工作模式：电子密码本模式（ECB）、加密分组链接模式（CBC）、加密反馈模式（CFB）和输出反馈模式（OFB）
     * <p>填充模式采用PKCS5Padding
     * <p>不指定工作模式、填充模式和初始化向量，采用默认实现
     */
    @SuppressLint("TrulyRandom")
    public static String encryptDES(String src, byte[] keyBytes, byte[] ivBytes) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        DESKeySpec ks = new DESKeySpec(keyBytes);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = skf.generateSecret(ks);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec, secureRandom);
        // 先加密再编码
        char[] chars = Base64Utils.encode(
                cipher.doFinal(src.getBytes(Charset.forName("UTF-8"))));
        return new String(chars);
    }

    public static String decryptDES(String src, byte[] keyBytes, byte[] ivBytes) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec, secureRandom);
        // 先解码再解密
        byte[] bytes = cipher.doFinal(Base64Utils.decode(src.toCharArray()));
        return new String(bytes, "UTF-8");
    }

    /**
     * AES加密算法
     * @param key aabbccddeeffgghh
     */
    public static String encryptAES(String input, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(input.getBytes());
        return new String(Base64Utils.encode(encrypted));
    }
}
