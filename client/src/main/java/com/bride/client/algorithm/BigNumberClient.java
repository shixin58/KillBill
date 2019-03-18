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
        someTest();
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

    public static void someTest() {
        BigInteger bigInteger = new BigInteger("9999999999");
        BigInteger bigIntegerAnother = new BigInteger("333");
        BigInteger result = bigInteger.add(bigIntegerAnother);
        System.out.println("BigInteger addition: "+result);

        BitSet bitSet = new BitSet();
        bitSet.set(0, true);
        bitSet.set(1, false);
        bitSet.clear(2);
        bitSet.set(3, 8);
        String bits = bitSet.toString();
        System.out.println("BitSet: "+bits+"; length: "+bitSet.length());

        int i = '0';
        int A = 'A';
        int a = 'a';
        int z = 'z';
        System.out.println("0: "+i+"; A: "+A+"; a: "+a+"; z: "+z);
    }
}
