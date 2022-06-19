package com.bride.client.algorithm;

import java.math.BigInteger;

/**
 * 1）求一个数二进制形式含1的个数。<p>
 * 2）附加题, 用位运算模拟乘除法。
 * <p>Created by shixin on 2019/3/18.
 */
public class OneNumClient {

    public static void main(String[] args) {

        System.out.println("oneNum "+oneNum(7));
        System.out.println("oneNumNew "+ oneNumOptimized(7));
        System.out.println("multiply: " + multiply(6, 17));
        try {
            System.out.println("divide: " + divide(Integer.MIN_VALUE, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 使用按位与和右移运算符，假定n > 0
    public static int oneNum(int n) {
        if (n<=0) return 0;
        int count = 0;
        while (n > 0) {
            if((n & 1) == 1)
                count++;
            n = n >> 1;
        }
        return count;
    }

    public static int oneNumOptimized(int n) {
        if (n<=0) return 0;
        int count = 0;
        for(;n>0;count++) {
            // 每次迭代最低位的1变成0
            n = n & (n-1);
        }
        return count;
    }

    /**
     * 模拟乘法
     * @param multiplicand 被乘数
     * @param multiplier 乘数
     * @return 乘法运算结果
     */
    public static int multiply(int multiplicand, int multiplier) {
        int result = 0;
        // 统计右移位数
        int count = 0;
        while (multiplier > 0) {
            if ((multiplier & 1) == 1) {
                result += (multiplicand << count);
            }
            count++;
            multiplier = multiplier >> 1;
        }
        return result;
    }

    /**
     * 模拟除法
     * @param dividend 被除数
     * @param divisor 除数
     * @return 商。结果可能溢出，所以使用BigInteger
     */
    public static BigInteger divide(final int dividend, final int divisor) throws ArithmeticException {
        // 除数不为0
        if (divisor == 0) throw new ArithmeticException("/ by zero");
        if (dividend == 0) return BigInteger.ZERO;

        // 1）处理除数为MIN_VALUE的情况
        if (divisor == Integer.MIN_VALUE) {
            if (dividend == Integer.MIN_VALUE) {
                return BigInteger.ONE;
            } else {
                return BigInteger.ZERO;
            }
        }

        // 2）处理被除数为MIN_VALUE的情况，取绝对值前将其适当截断
        int quotient = 0;
        int tmpDividend = dividend;
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == -1) {
                // int溢出
                return BigInteger.valueOf(1L + Integer.MAX_VALUE);
            } else if(divisor > 0){
                tmpDividend += divisor;
                quotient--;
            } else {
                tmpDividend -= divisor;
                quotient++;
            }
        }

        // 3）处理被除数或除数为负的情况。被除数和除数都取绝对值，用positive标记结果的正负
        boolean positive = true;
        int absDividend = tmpDividend, absDivisor = divisor;
        if (tmpDividend < 0 && divisor < 0) {
            absDividend = Math.abs(tmpDividend);
            absDivisor = Math.abs(divisor);
        } else if (tmpDividend < 0) {
            absDividend = Math.abs(tmpDividend);
            positive = false;
        } else if (divisor < 0) {
            absDivisor = Math.abs(divisor);
            positive = false;
        }
        System.out.println("absDividend = "+absDividend+"; absDivisor = "+absDivisor);
        if (absDividend < absDivisor) {
            return BigInteger.valueOf(quotient);
        }

        // 4）求出被除数和除数二进制形式最高位1所在位置。迭代执行二进制右移运算
        int dividendHighestPos = 0;
        int divisorHighestPos = 0;
        int tmpOperand = absDividend;
        while (tmpOperand > 0) {
            dividendHighestPos++;
            tmpOperand = tmpOperand >> 1;
        }
        tmpOperand = absDivisor;
        while (tmpOperand > 0) {
            divisorHighestPos++;
            tmpOperand = tmpOperand >> 1;
        }
        System.out.println("dividendHighestPos = "+dividendHighestPos+"; divisorHighestPos = "+divisorHighestPos);

        // 5）核心算法。利用二进制左移运算模拟除法
        int absQuotient = 0;
        int highestInterval = dividendHighestPos - divisorHighestPos;
        while (absDividend >= absDivisor) {
            if (absDividend >= (absDivisor << highestInterval)) {
                absDividend -= (absDivisor << highestInterval);
                absQuotient += (1 << highestInterval);
            }
            highestInterval--;
        }

        quotient += positive?absQuotient:-absQuotient;
        return BigInteger.valueOf(quotient);
    }
}
