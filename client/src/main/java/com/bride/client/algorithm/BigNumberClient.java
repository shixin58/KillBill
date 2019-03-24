package com.bride.client.algorithm;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * <p>Created by shixin on 2019/3/18.
 */
public class BigNumberClient {
    public static void main(String[] args) {
//        additionOrSubtraction();
//        testBitSet();
//        testAscii();
//        swap();
        move();
    }

    public static void additionOrSubtraction() {
        // 仅含+, -, 0
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter: ");
        if (scanner.hasNext()) {
            String input = scanner.next();

            StringBuilder value = new StringBuilder();
            int i = 0;
            if(input.charAt(0) == '+' || input.charAt(0) == '-') {
                i = 1;
                value.append(input.charAt(0));
            }
            int length = input.length();
            LinkedList<Integer> integers = new LinkedList<>();
            LinkedList<Character> characters = new LinkedList<>();
            for (;i<length;i++) {
                char c = input.charAt(i);
                if (c == '+' || c == '-') {
                    characters.add(c);
                    integers.add(
                            Integer.parseInt(value.toString()));
                    value.delete(0, value.length());
                } else {
                    value.append(c);
                }
            }
            integers.add(Integer.parseInt(value.toString()));
            value.delete(0, value.length());

            int result = integers.removeFirst();
            while (!integers.isEmpty()) {
                int operand = integers.removeFirst();
                char operator = characters.removeFirst();
                if (operator == '+') {
                    result += operand;
                } else {
                    result -= operand;
                }
            }
            System.out.println("The result is "+result);
        }
    }

    public static void testBigInteger() {
        BigInteger bigInteger = new BigInteger("9999999999");
        BigInteger bigIntegerAnother = new BigInteger("333");
        BigInteger result = bigInteger.add(bigIntegerAnother);
        System.out.println("BigInteger addition: "+result);
    }

    public static void testBitSet() {
        BitSet bitSet = new BitSet();
        bitSet.set(0, true);
        bitSet.set(1, false);
        bitSet.set(2);
        bitSet.clear(3);
        bitSet.set(4, 8);
        String bits = bitSet.toString();
        System.out.println("bitSet: "+bits+"; length: "+bitSet.length());

        BitSet anotherBitSet = new BitSet();
        anotherBitSet.set(0, 3);
        anotherBitSet.clear(4, 7);
        anotherBitSet.set(7);
        System.out.println("anotherBitSet: "+anotherBitSet.toString()+"; length: "+anotherBitSet.length());

        bitSet.xor(anotherBitSet);
        anotherBitSet.xor(bitSet);
        bitSet.xor(anotherBitSet);
        System.out.println(bitSet.toString()+", "+anotherBitSet);
    }

    // 将字符强转为ASCII
    public static void testAscii() {
        int i = '0';
        int A = 'A';
        int a = 'a';
        int z = 'z';
        System.out.println("0: "+i+"; A: "+A+"; a: "+a+"; z: "+z);
    }

    // 异或XOR运算法则：(a^b)^c == a^(b^c)
    public static void swap() {
        int a = 5;
        int b = 9;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a = "+a+"; b = "+b);
    }

    public static void move() {
        // 真值，机器数
        // 正数原码、反码、补码均相同
        // 计算机使用补码运算
        System.out.println(">>"+(Integer.MIN_VALUE >> 1));
        System.out.println(">>"+(Integer.MIN_VALUE >> 31));

        // 负数高位补0
        System.out.println(">>>"+(Integer.MIN_VALUE >>> 1));
        System.out.println(">>>"+(Integer.MIN_VALUE >>> 2));
        System.out.println(">>>"+(-1 >>> 1));
    }
}
