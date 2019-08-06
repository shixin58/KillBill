package com.bride.client.strings;

import java.util.Formatter;
import java.util.UUID;

/**
 * 1、String, StringBuilder, StringBuffer比较
 * String, StringBuilder, StringBuffer均实现Serializable和CharSequence接口，可以持久化到文件，通过Bundle传输。
 * <p>Created by shixin on 2018/10/31.
 */
public class StringClient {

    public static void main(String[] args) {
        testString();
        exploreMemory();
        testStringBuilder();
        testStringBuffer();
        generateUUID();
    }

    public static void testString() {
        System.out.println("*** String ***");
        char[] chars = {'C', 'h', 'i', 'n', 'a'};
        System.out.println(new String(chars));
        // 不同String，hashCode可能相等；hashCode计算过程numeric overflow, 强转为int
        int i = Integer.MAX_VALUE + 100;
        long j = Integer.MAX_VALUE + 100L;
        System.out.println("value of i is "+i+"; value of j is "+(int)j);
        System.out.println("hexadecimal string representation of Integer.MIN_VALUE is "+Integer.toHexString(Integer.MIN_VALUE));
        String str = "DDDABCDEFGHIJKLMN";
        System.out.println("hashcode of "+str+" is "+str.hashCode());

        Formatter formatter = new Formatter(System.out);
        char u = 'a';
        formatter.format("c: %c\n", u);
        formatter.format("h: %h\n", u);// 十六进制
        System.out.println(Character.valueOf(u).hashCode()+" "+(int)u);
        formatter.format("%%h: %h, %h\012", '石', '鑫');
        formatter.format("%%c: %c, %c\012", '\u77f3', '\u946b');// \n
        formatter.format("%%c: %c\n", '\060');// 0
        int k = 65;
        System.out.println(new Formatter(new StringBuffer()).format("%%d: %d", k).toString());
        System.out.println(String.format("%%x: %x", k));
        System.out.println(String.format("%%c: %c", k));
        double x = 179.543;
        System.out.printf("%%e: %.4e\n", x);
        System.out.format("%%f: %-10.2f\n", x);
    }

    public static void exploreMemory() {
        System.out.println("\n*** exploreMemory ***");
        Object o = new Object();
        // 打印内存地址(java.lang.Object@60e53b93)
        System.out.println(o.toString());

        // 不同对象，相同hash值
        System.out.println("==: "+(new String("hi") == new String("hi")));
        // VM constant pool
        System.out.println("intern: "+(new String("hi").intern() == new String("hi").intern()));
        System.out.println("hashCode: "+new String("hi").hashCode() +" " +new String("hi").hashCode());

        String str = "0xFF";// 255
        System.out.println(Integer.decode(str)+" "+str.hashCode());
    }

    public static void testStringBuilder() {
        System.out.println("\n*** StringBuilder ***");
        // 内部维护char[]，不能前插
        StringBuilder stringBuilder = new StringBuilder(16);
        stringBuilder.append(5)
                .append(',')
                .append(5.1f)
                .append(true)
                .append("OK")
                .append(new char[]{'H', 'i', ',', '石', '鑫'});

        stringBuilder.length();
        stringBuilder.trimToSize();
        stringBuilder.capacity();
        stringBuilder.subSequence(0, 1);

        // StringBuilder未覆盖hashCode和equals方法
        stringBuilder.equals("");

        System.out.println(stringBuilder);
        // for循环对称位置交换
        // 用StringFactory#newStringFromChars(int, int, char[])生成String
        System.out.println(stringBuilder.reverse());
        stringBuilder.replace(0, 2, "A");
        System.out.println(stringBuilder);
        System.out.println("hashcode is "+stringBuilder.hashCode());
        if (stringBuilder.charAt(0) >= 'A' && stringBuilder.charAt(0) <= 'Z') {
            stringBuilder.setCharAt(0, 'a');
        }
        System.out.println(stringBuilder);
        System.out.println("hashcode is "+stringBuilder.hashCode());
    }

    public static void testStringBuffer() {
        System.out.println("\n*** StringBuffer ***");
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

    public static void generateUUID() {
        System.out.println("\n*** UUID ***");
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(UUID.randomUUID().toString());
    }
}
