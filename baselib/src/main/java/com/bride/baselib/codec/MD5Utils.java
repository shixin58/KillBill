package com.bride.baselib.codec;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Message-Digest Algorithm
 * <p>输入任意长度的信息，经过处理，输出为128位的信息（数字指纹）；不同的输入得到的不同的结果（唯一性）；</p>
 * <p>Created by shixin on 2018/4/26.
 */
public class MD5Utils {

    private static final int BUFFER_LENGTH = 1024;

    public static String getMD5(String val) {
        if(val==null) {
            return null;
        }
        return getMD5(val.getBytes());
    }

    public static String getMD5(byte[] val) {
        String rst = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(val);
            byte[] b = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                int temp = 0xFF & b[i];
                String s = Integer.toHexString(temp);
                if (temp <= 0x0F) {
                    s = "0" + s;
                }
                sb.append(s);
            }
            rst = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return rst;
    }

    public static String sha1(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            StringBuffer buf = new StringBuffer();
            byte[] bits = md.digest();
            for(int i=0;i<bits.length;i++){
                int a = bits[i];
                if(a<0) a+=256;
                if(a<16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取单个文件的MD5值！
     */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte buffer[] = new byte[BUFFER_LENGTH];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, BUFFER_LENGTH)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return toHex(digest.digest());
    }

    private static String toHex(byte bytes[]) {
        String hexString = "";
        for (byte b : bytes) {
            int temp = b & 255;
            if (temp < 16 && temp >= 0) {
                // 手动补上一个“0”
                hexString = hexString + "0" + Integer.toHexString(temp);
            } else {
                hexString = hexString + Integer.toHexString(temp);
            }
        }
        return hexString;
    }
}
