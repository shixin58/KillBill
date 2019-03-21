package com.bride.client;

/**
 * 1、String, StringBuilder, StringBuffer比较
 * <p>Created by shixin on 2018/10/31.
 */
public class StringClient {

    public static void main(String[] args) {
        testStringBuilder();
        testStringBuffer();
    }

    public static void testStringBuilder() {
        System.out.println("*** StringBuilder ***");
        // 内部维护char[]，不能前插
        StringBuilder stringBuilder = new StringBuilder(16);
        stringBuilder.append(5)
                .append(',')
                .append(5.1f)
                .append(true)
                .append("OK");

        stringBuilder.length();
        stringBuilder.trimToSize();
        stringBuilder.capacity();
        stringBuilder.subSequence(0, 1);

        System.out.println(stringBuilder);
        // for循环对称位置交换
        // 用StringFactory#newStringFromChars(int, int, char[])生成String
        System.out.println(stringBuilder.reverse());
    }

    public static void testStringBuffer() {
        System.out.println("*** StringBuffer ***");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Love");

        stringBuffer.length();
        stringBuffer.trimToSize();

        // 获取数组大小
        stringBuffer.capacity();

        // 给原数组扩容
        stringBuffer.ensureCapacity(5);

        stringBuffer.charAt(0);
        stringBuffer.subSequence(0, 1);

        stringBuffer.delete(0, 1);
        System.out.println(stringBuffer);
    }
}
