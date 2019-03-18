package com.bride.client.algorithm;

/**
 * 钢条切割
 * <p>Created by shixin on 2018/9/17.
 */
public class CutSteelClient {

    public static void main(String[] args) {
        cutSteel();
        cutSteelMemo();
        cutSteelBottomUp();
    }

    public static void cutSteel() {
        int[] pieces = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        System.out.println("cutSteel "+cut(pieces, 4));
    }

    private static int cut(int[] p, int n) {
        if(n==0)return 0;
        int q = -1;
        for(int i=1;i<=n;i++) {
            q = Math.max(q, p[i-1]+cut(p, n-i));
        }
        return q;
    }

    public static void cutSteelMemo() {
        int[] pieces = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        int[] r = new int[pieces.length+1];
        for(int i=0;i<r.length;i++) {
            r[i] = -1;
        }
        System.out.println("cutSteelMemo "+cut(pieces, 4, r));
    }

    private static int cut(int[] p, int n, int[] r) {
        if(r[n]>=0)
            return r[n];
        if(n==0)
            return r[0]=0;
        int q = -1;
        for(int i=1;i<=n;i++) {
            q = Math.max(q, p[i-1]+cut(p, n-i, r));
        }
        return r[n]=q;
    }

    public static void cutSteelBottomUp() {
        int n = 4;
        int[] pieces = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        int[] r = new int[n+1];
        r[0] = 0;
        for(int i=1;i<=n;i++) {
            int q = -1;
            for(int j=1;j<=i;j++)
                q = Math.max(q, pieces[j-1]+r[i-j]);
            r[i] = q;
        }
        System.out.println("cutSteelBottomUp "+r[n]);
    }
}
