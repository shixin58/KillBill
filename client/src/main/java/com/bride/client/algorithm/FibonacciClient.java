package com.bride.client.algorithm;

/**
 * 斐波那契数列
 * <p>Created by shixin on 2019/3/18.
 */
public class FibonacciClient {

    static int numF = 0;
    static int numMF = 0;

    public static void main(String[] args) {
        int n = 30;
        long start = System.nanoTime();
        System.out.println("fibonacci "+fibonacci(n)+", Recursive times is "+numF+", It takes "+(System.nanoTime() - start)+" ns");
        start = System.nanoTime();
        System.out.println("memoizedFibonacci "+memoizedFibonacci(n)+", Recursive times is "+numMF+", It takes "+(System.nanoTime() - start)+" ns");
        start = System.nanoTime();
        System.out.println("bottomUpFibonacci "+ bottomUpFibonacci(n)+", It takes "+(System.nanoTime() - start)+" ns");
    }

    // 递归形式recursive method, n>=0
    public static int fibonacci(int n) {
        numF++;
        if(n <= 0) return 0;
        if(n == 1) return 1;
        return fibonacci(n-1) + fibonacci(n-2);
    }

    public static int memoizedFibonacci(int n) {
        int[] r = new int[n+1];
        for (int i=0; i<r.length; i++) {
            r[i] = -1;
        }
        return memoizedFibonacciAux(n, r);
    }

    private static int memoizedFibonacciAux(int n, int[] r) {
        numMF++;
        if (r[n] >= 0) return r[n];
        if (n <= 0) return r[n] = 0;
        if (n == 1) return r[n] = 1;
        return r[n] = memoizedFibonacciAux(n-2, r)+memoizedFibonacciAux(n-1, r);
    }

    public static int bottomUpFibonacci(int n) {
        if(n<=0) return 0;
        if(n==1) return 1;
        int[] r = new int[n+1];
        r[0] = 0;
        r[1] = 1;
        for(int i=2; i<=n; i++) {
            r[i] = r[i-1]+r[i-2];
        }
        return r[n];
    }
}
