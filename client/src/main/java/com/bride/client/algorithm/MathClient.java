package com.bride.client.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>Created by shixin on 2019-06-04.
 */
public class MathClient {

    public static void main(String[] args) {
        testRandom();
    }

    public static void testRandom() {
        Random random = new Random(47);
        // [0.0, 1.0)
        System.out.println("double: "+random.nextDouble());

        // [0, 100)
        System.out.println("int: "+random.nextInt(100));

        byte[] buf = new byte[16];
        random.nextBytes(buf);
        System.out.println("byte[]: "+Arrays.toString(buf));
    }
}
