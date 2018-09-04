package com.max.baselib;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

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
 * <li>Java DES(Data Encryption Standard)加解密
 * <li>Java AES(Advanced Encryption Standard)加密
 * <p>Created by shixin on 2018/4/25.
 */
public class DataEncryptionAlgorithm {

    public static final String PASSWORD = "12345678";

    /**
     * 私钥。DES固定8bytes，AES16/24/32bytes
     */
    public static byte[] DES_KEY_BYTES = PASSWORD.getBytes();

    /**
     * 初始化向量参数。DES固定8bytes，AES固定16bytes
     */
    public static byte[] DES_IV_BYTES = {2,5,2,6,3,6,7,2};

    public static String DES_KEY = "mrXn5pHX";

    public static String DES_IV = "k9589Iau";

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


    public static String encryptDES(String src) throws Exception {
        return encryptDES(src, DES_KEY.getBytes(), DES_IV.getBytes());
    }

    /**
     * DES解密算法
     * @param src 服务器返回的加密字符串
     */
    public static String decryptDES(String src) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(DES_KEY_BYTES);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(DES_IV_BYTES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec, secureRandom);
        // 先解码再解密
        byte[] bytes = cipher.doFinal(Base64Utils.decode(src.toCharArray()));
        return new String(bytes, "UTF-8");
    }

    /**
     * AES加密算法
     * @param key aabbccddeeffgghh
     */
    public static String encryptAES(@NonNull String input, @NonNull String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(input.getBytes());
        return new String(Base64Utils.encode(encrypted));
    }

    /**
     * DES加密算法
     * <p>四种工作模式：电子密码本模式（ECB）、加密分组链接模式（CBC）、加密反馈模式（CFB）和输出反馈模式（OFB）
     * <p>填充模式采用PKCS5Padding
     * <p>不指定工作模式、填充模式和初始化向量，采用默认实现
     * @param src 要加密的字符串
     * @param keyBytes {@link String#getBytes()}
     * @param ivBytes {@link String#getBytes()}
     */
    @SuppressLint("TrulyRandom")
    @NotNull
    public static String encryptDES(@NotNull String src, byte[] keyBytes, byte[] ivBytes) throws Exception {
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
}
