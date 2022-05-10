package com.bride.client.algorithm;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Random;

/**
 * <p>Created by shixin on 2019-06-04.
 */
public class MathClient {

    public static void main(String[] args) {
        testRandom();
        testMath();
        mathContext();
    }

    public static void testRandom() {
        Random random = new Random(47);
        // [0.0, 1.0)
        System.out.println("double: "+random.nextDouble());

        // [0, 100)
        System.out.println("int: "+random.nextInt(100));

        // byte[-128, 127]
        byte[] buf = new byte[16];
        random.nextBytes(buf);
        System.out.println("byte[]: "+Arrays.toString(buf));
    }

    // 对比ceil(double)/floor(double)/round(double)
    public static void testMath() {
        float origin = -3.1415f;
        int result = (int) Math.floor(origin+0.5f);
        // 小数位四舍五入
        int terminal = Math.round(origin);
        System.out.printf("result = %d, terminal = %d\n", result, terminal);
    }

    public static void mathContext() {
        MathContext mc = new MathContext(8);
        BigDecimal bd = new BigDecimal(1.1, mc);
        // the Unit in the Last Place, 1E-7=1*10^-7
        System.out.println(bd + " " + bd.ulp());

        // prime质数/素数, certainty确定性
        BigInteger bi = BigInteger.valueOf(17L);
        System.out.println(bi.nextProbablePrime());
        System.out.println(bi.isProbablePrime(5));
    }
}
