package com.bride.demon;


import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

public class EncryptDecryptUtils {


    private static final String ENCODE = "UTF-8";
    //AES算法加解密模式有多种，这里选择 CBC + PKCS5Padding 模式，CBC 需要一个AES_IV偏移量参数，而AES_KEY是密钥
    private static final String AES = "AES";
    private static final String AES_IV = "abzdfxercmriowps";
    private static final String AES_KEY = "LuckyGoldZWHL809"; //16字节，128bit，三种密钥长度中的一种
//    private static final String AES_KEY = "NIMByTiMKKINA001"; //16字节，128bit，三种密钥长度中的一种
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * AES加密后再Base64编码，输出密文。注意AES加密的输入是二进制串，所以需要先将UTF-8明文转成二进制串
     */
    public static String doEncryptEncode(String content) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(ENCODE), AES);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(AES_IV.getBytes(ENCODE)));
            //1. 先获取二进制串，再进行AES（CBC+PKCS5Padding)模式加密
            byte[] result = cipher.doFinal(content.getBytes(ENCODE));
            //2. 将二进制串编码成BASE64串
//            return Base64.getEncoder().encodeToString(result);
            return Base64.encodeToString(result, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Base64解码后再进行AES解密，最后对二进制明文串进行UTF-8编码输出明文串
     */
    public static String doDecodeDecrypt(String content) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(ENCODE), AES);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(AES_IV.getBytes(ENCODE)));
        //1. 先将BASE64密文串解码成二进制串
//        byte[] base64 = Base64.getDecoder().decode(content);
        byte[] base64 = Base64.decode(content, Base64.NO_WRAP);
        //2. 再将二进制密文串进行AES(CBC+PKCS5Padding)模式解密
        byte[] result = cipher.doFinal(base64);
        //3. 最后将二进制的明文串以UTF-8格式编码成字符串后输出
        return new String(result, Charset.forName(ENCODE));
    }

}
