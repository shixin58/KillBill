package com.bride.client.algorithm;

/**
 * 斐波那契数列
 * <p>Created by shixin on 2019/3/18.
 */
public class FibonacciClient {

    public static void main(String[] args) {
        System.out.println("fibonacci "+fibonacci(4));
        System.out.println("fibonacciBottomUp "+fibonacciBottomUp(4));
    }

    // 递归形式recursive method
    public static int fibonacci(int n) {
        if(n<=0) return 0;
        if(n==1) return 1;
        return fibonacci(n-1) + fibonacci(n-2);
    }

    public static int fibonacciBottomUp(int n) {
        if(n<=0) return 0;
        if(n==1) return 1;
        int[] r = new int[n+1];
        r[0] = 0;
        r[1] = 1;
        for(int i=2;i<=n;i++) {
            r[i] = r[i-1]+r[i-2];
        }
        return r[n];
    }
}
