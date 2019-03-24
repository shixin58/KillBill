package com.bride.client.algorithm;

/**
 * 求一个数二进制形式含1的个数
 * <p>Created by shixin on 2019/3/18.
 */
public class OneNumClient {

    public static void main(String[] args) {

        System.out.println("oneNum "+oneNum(7));
        System.out.println("oneNumNew "+ oneNumNew(7));
        System.out.println("multiply: " + multiply(6, 17));
        System.out.println("divide: " + divide(Integer.MIN_VALUE, 1));
    }

    // 使用按位与和右移运算符，假定n > 0
    public static int oneNum(int n) {
        int count = 0;
        while (n > 0) {
            if((n & 1) == 1)
                count++;
            n = n >> 1;
        }
        return count;
    }

    public static int oneNumNew(int n) {
        int count = 0;
        for(;n>0;count++) {
            n = n & (n-1);
        }
        return count;
    }

    public static int multiply(int a, int b) {
        int result = 0;
        int count = 0;
        while (b > 0) {
            if ((b&1) == 1) {
                result += (a << count);
            }
            count++;
            b = b >> 1;
        }
        return result;
    }

    public static int divide(int dividend, int divisor) {
        int quotient = 0;
        if (divisor == Integer.MIN_VALUE) {
            if (dividend == Integer.MIN_VALUE) {
                return 1;
            } else {
                return 0;
            }
        }
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == -1) {
                return Integer.MAX_VALUE;
            } else if(divisor > 0){
                dividend += divisor;
                quotient--;
            } else {
                dividend -= divisor;
                quotient++;
            }
        }
        boolean positive = true;
        if (dividend < 0 && divisor < 0) {
            dividend = Math.abs(dividend);
            divisor = Math.abs(divisor);
        } else if (dividend < 0) {
            dividend = Math.abs(dividend);
            positive = false;
        } else if (divisor < 0) {
            divisor = Math.abs(divisor);
            positive = false;
        }

        System.out.println("dividend = "+dividend+"; divisor = "+divisor);
        if (dividend < divisor) {
            return quotient;
        }

        // 求出被除数和除数最高位1所在位置
        int divisorHighestPos = 0;
        int dividendHighestPos = 0;
        int tmp = divisor;
        while (tmp > 0) {
            divisorHighestPos++;
            tmp = tmp >> 1;
        }
        tmp = dividend;
        while (tmp > 0) {
            dividendHighestPos++;
            tmp = tmp >> 1;
        }
        System.out.println("dividendHighestPos = "+dividendHighestPos+"; divisorHighestPos = "+divisorHighestPos);

        int tmpQuotient = 0;
        int interval = dividendHighestPos - divisorHighestPos;
        while (dividend >= divisor && interval >= 0) {
            if (dividend >= (divisor << interval)) {
                dividend -= (divisor << interval);
                tmpQuotient += (1<<interval);
            }
            interval--;
        }
        quotient += positive?tmpQuotient:-tmpQuotient;
        return quotient;
    }
}
