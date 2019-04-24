package com.bride.client;

import java.io.IOException;

/**
 * Throwable实现Serializable，是Exception和Error的父类。
 * <p>Created by shixin on 2019-04-20.
 */
public class ThrowableClient {

    public static void main(String[] args) {

        testRuntimeException(1);

        try {
            testException();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        testError();

        System.out.println(testException(-1));
    }

    static void testException() throws IOException, ClassNotFoundException {

    }

    // RuntimeException doesn't have to be caught. For compiler, it's unchecked exceptions.
    static void testRuntimeException(int i) {
        switch (i) {
            case 1:
                throw new ArithmeticException("1/0");// judge if divisor is zero
            case 2:
                throw new ClassCastException("(String)Integer");// instanceof
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
        try {
            throw new OutOfMemoryError("Bitmap过大");
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    // 返回finally中的return值
    static int testException(int i) {
        try {
            if (i == -1) {
                throw new Exception("参数不能为-1");
            }
            return print(1);
        } catch (Exception e) {
            return print(2);
        } finally {
            return print(3);
        }
    }

    static int print(int i) {
        System.out.println("print "+i);
        return i;
    }
}
