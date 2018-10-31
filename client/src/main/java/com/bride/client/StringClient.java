package com.bride.client;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <p>Created by shixin on 2018/10/31.
 */
public class StringClient {
    public static void main(String[] args) {
//        testStringBuilder();
//        testStringBuffer();

        final StringClient stringClient = new StringClient();
        Executor executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                StringClient.printD();
            }
        });
        /*executor.execute(new Runnable() {
            @Override
            public void run() {
                stringClient.printA();
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                stringClient.printB();
            }
        });*/
        executor.execute(new Runnable() {
            @Override
            public void run() {
                StringClient.printC();
            }
        });
    }

    public static void testStringBuilder() {
        // 内部维护char[]，不能前插
        StringBuilder stringBuilder = new StringBuilder(16);
        stringBuilder.append(5)
                .append(',')
                .append(5.1f)
                .append(true)
                .append("OK");
        // for循环对称位置交换
        // 用StringFactory#newStringFromChars(int, int, char[])生成String
        stringBuilder.reverse()
                .toString();
    }

    public static void testStringBuffer() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Love");
        // 获取数组大小
        stringBuffer.capacity();
        stringBuffer.length();
        stringBuffer.trimToSize();
        // 给原数组扩容
        stringBuffer.ensureCapacity(5);
        stringBuffer.charAt(0);
        stringBuffer.subSequence(0, 1);
        stringBuffer.delete(0, 1);
    }

    public synchronized void printA() {
        System.out.println("A start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("A end "+Thread.currentThread().getName());
    }

    public synchronized void printB() {
        System.out.println("B start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("B end "+Thread.currentThread().getName());
    }

    public synchronized static void printC() {
        System.out.println("C start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("C end "+Thread.currentThread().getName());
    }

    public synchronized static void printD() {
        System.out.println("D start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("D end "+Thread.currentThread().getName());
    }
}
