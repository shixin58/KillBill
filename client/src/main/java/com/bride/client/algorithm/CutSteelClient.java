package com.bride.client.algorithm;

/**
 * 用动态规划(Dynamic programming)求解最优化问题
 * 钢条切割。公司购买一根长钢管，欲将其切割成短钢管出售。给定钢管长度和对应价格，给出最佳的切割方案，使得收益最大。
 * <p>Created by shixin on 2018/9/17.
 */
public class CutSteelClient {

    // 价格对照表，分别代表1、2、3、4、5、6、7、8、9、10英寸钢条的价格。
    static int[] prices = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};

    public static void main(String[] args) {

        System.out.println("cutSteel "+cutSteelRecursive(prices, 4));

        System.out.println("cutSteelMemo "+cutSteelMemo(prices, 4));

        System.out.println("cutSteelBottomUp "+cutSteelBottomUp(prices, 4));
    }

    /**
     * 1）递归求解
     * @param prices 价格对照表
     * @param n 长度为n英寸的钢条, n取值[1, 10]
     * @return 最大收益, 即最优解的值
     */
    public static int cutSteelRecursive(int[] prices, int n) {
        if(n <= 0) return 0;
        int earnings = 0;
        for(int i=1; i<=n; i++) {
            earnings = Math.max(earnings, prices[i-1] + cutSteelRecursive(prices, n-i));
        }
        return earnings;
    }

    /**
     * 2）备忘化递归求解
     * @param p
     * @param n
     * @return
     */
    public static int cutSteelMemo(int[] p, int n) {
        if(n <= 0) return 0;
        // 尺寸<=n的钢板切割后的最大收益
        int[] r = new int[n+1];
        r[0] = 0;
        for(int i=1; i<r.length; i++) {
            r[i] = -1;
        }
        return cutSteelMemoAux(p, n, r);
    }

    private static int cutSteelMemoAux(int[] p, int n, int[] r) {
        if(r[n] >= 0) return r[n];

        int maxIncome = 0;
        for(int i=1; i<=n; i++) {
            maxIncome = Math.max(maxIncome, p[i-1]+ cutSteelMemoAux(p, n-i, r));
        }
        return r[n] = maxIncome;
    }

    /**
     * 3）迭代求解
     * @param p
     * @param n
     * @return
     */
    public static int cutSteelBottomUp(int[] p, int n) {
        if (n <= 0) return 0;
        int[] r = new int[n+1];

        for(int i=1; i<=n; i++) {
            // 求长度为i的钢板切割最大收益
            int maxIncome = 0;
            for(int j=1; j<=i; j++)
                maxIncome = Math.max(maxIncome, p[j-1]+r[i-j]);
            r[i] = maxIncome;
        }
        return r[n];
    }
}
