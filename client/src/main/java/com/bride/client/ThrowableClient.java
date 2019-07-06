package com.bride.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Throwable实现Serializable，是Exception和Error的父类。
 * <p>Created by shixin on 2019-04-20.
 */
public class ThrowableClient {

    static int count = 0;

    public static void main(String[] args) {

        testRuntimeException(2);

        try {
            testException();
        } catch (Exception e) {
            e.printStackTrace();
        }

        testError();

        System.out.println(testException(1));

        reviewArrayList();
    }

    // 实际代码并未抛出IOException和ReflectiveOperationException，先占位
    static void testException() throws IOException, ReflectiveOperationException, InterruptedException {
        try {
            System.out.println("throw InterruptedException");
            throw new InterruptedException();
        } finally {
            // 异常丢失
            return;
        }
    }

    // RuntimeException doesn't have to be caught. For compiler, it's unchecked exceptions.
    static void testRuntimeException(int i) throws ArithmeticException, ClassCastException {
        System.out.println("--- testRuntimeException ---");
        switch (i) {
            case 1:
                throw new ArithmeticException("1/0");// judge if divisor is zero
            case 2:
                try{
                    throw new ClassCastException("(String)Integer");// instanceof
                } finally {
                    // 异常被覆盖
                    throw new ArrayStoreException();
                }
            case 3:
                throw new IndexOutOfBoundsException("Array length is 2, call a[2]");// check in advance if index is out of bounds
            case 4:
                throw new NullPointerException("null.toString()");// judge if it's null ahead of time
            case 5:
                throw new IllegalStateException("参数不能为-1");// check if parameter is legal
        }
    }

    // Error不需要try-catch
    static void testError() throws OutOfMemoryError {
        System.out.println("--- testError ---");
        try {
            throw new OutOfMemoryError("Bitmap过大");
        } catch (Throwable e) {
            e.printStackTrace(System.err);
        } finally {
            System.err.println("Bingo!");
            try {
                System.out.println("read: "+(char)System.in.read());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 返回finally中的return值，之前的return关键字失效
    static int testException(int i) {
        System.out.println("--- testException ---");
        try {
            if (i == -1) {
                throw new Exception("参数不能为-1");
            }
            return print(i++);
        } catch (Exception e) {
            return print(i++);
        } finally {
            return print(i++);
        }
    }

    static int print(int i) {
        System.out.println("print "+i);
        return i;
    }

    // 递归方法模拟StackOverflowError, 约2^13
    public static void increment() {
        count++;
        if (count > 10_000) {
            System.out.println("exit: "+count);
            return;
        }
        try {
            increment();
        } catch (Throwable e) {
            System.out.println("catch: "+count);
            throw e;
        }
    }

    public static void reviewArrayList() {
        System.out.println("--- reviewArrayList ---");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Joyful");
        Logger.getLogger("Victor").log(Level.WARNING, "Catch a cold!");
        arrayList.remove(0);
    }
}
