package com.bride.client.algorithm;

/**
 * 钢条切割
 * <p>Created by shixin on 2018/9/17.
 */
public class CutSteelClient {

    // 分别代表1、2、3、4、5、6、7、8、9、10英寸钢条的价格
    static int[] pieces = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};

    public static void main(String[] args) {

        System.out.println("cutSteel "+cutSteelRecursive(pieces, 4));

        System.out.println("cutSteelMemo "+cutSteelMemo(pieces, 4));

        System.out.println("cutSteelBottomUp "+cutSteelBottomUp(pieces, 4));
    }

    /**
     * 递归求解
     * @param p 价格对照表
     * @param n 长度为n英寸的钢条, n取值[0, 10]
     * @return 最优解的值
     */
    public static int cutSteelRecursive(int[] p, int n) {
        if(n==0)return 0;
        int q = -1;
        for(int i=1; i<=n; i++) {
            q = Math.max(q, p[i-1] + cutSteelRecursive(p, n-i));
        }
        return q;
    }

    public static int cutSteelMemo(int[] p, int n) {
        int[] r = new int[n+1];
        r[0] = 0;
        for(int i=1; i<r.length; i++) {
            r[i] = -1;
        }
        return cut(p, n, r);
    }

    private static int cut(int[] p, int n, int[] r) {

        if(r[n]>=0) return r[n];

        int q = -1;
        for(int i=1; i<=n; i++) {
            q = Math.max(q, p[i-1]+cut(p, n-i, r));
        }
        return r[n] = q;
    }

    public static int cutSteelBottomUp(int[] p, int n) {
        int[] r = new int[n+1];
        r[0] = 0;
        for(int i=1; i<=n; i++) {
            int q = -1;
            for(int j=1; j<=i; j++)
                q = Math.max(q, p[j-1]+r[i-j]);
            r[i] = q;
        }
        return r[n];
    }
}
