package com.bride.client.algorithm;

/**
 * 求一个数二进制形式含1的个数
 * <p>Created by shixin on 2019/3/18.
 */
public class OneNumClient {

    public static void main(String[] args) {

        System.out.println("oneNum "+oneNum(7));
        System.out.println("oneNumNew "+ oneNumNew(7));
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
}
