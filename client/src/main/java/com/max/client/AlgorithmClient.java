package com.max.client;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Created by shixin on 2018/9/17.
 */
public class AlgorithmClient {
    public static void main(String[] args) {
//        cutSteel();
//        cutSteelMemo();
//        cutSteelBottomUp();

//        System.out.println("fibonacci "+fibonacci(4));
//        System.out.println("fibonacciBottomUp "+fibonacciBottomUp(4));

//        System.out.println("lengthOfLongestSubstring "+lengthOfLongestSubstring("acebhhbdjloium"));
//        System.out.println("lengthOfLongestSubstring2 "+lengthOfLongestSubstring2("acebhhbdjloium"));
//        System.out.println("lengthOfLongestSubstring3 "+lengthOfLongestSubstring3("acebhhbdjloium"));

        System.out.println("oneNum "+oneNum(11));
        System.out.println("oneNum2 "+oneNum2(11));
    }

    // 钢条切割
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

    // 斐波那契数列
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

    // 最长不重复子串
    public static int lengthOfLongestSubstring(String s) {
        if(s==null) return 0;
        if(s.length()<=1) return s.length();
        int length = 1;
        for(int i=0;i<s.length();i++) {
            for(int j=i+1;j<s.length();j++) {
                // 从[i, j]搜索s[j]，复杂度O(j-i+1)
                int index = s.indexOf(s.charAt(j), i);
                if(index < j) {// repeat
                    if(index>i)
                        i=index;
                    break;
                }else {
                    if(j-i+1>length)
                        length = j-i+1;
                }
            }
        }
        return length;
    }

    public static int lengthOfLongestSubstring2(String s) {
        if(s==null) return 0;
        if(s.length()<=1) return s.length();
        int maxLength = 1;
        int length = s.length();
        HashSet<Character> set = new HashSet<>();
        for(int i=0;i<length;i++) {
            set.clear();
            set.add(s.charAt(i));
            for(int j=i+1;j<length;j++) {
                // contains和charAt复杂度均O(1)
                if(set.contains(s.charAt(j))) {
                    break;
                }
                set.add(s.charAt(j));
                if(j-i+1>maxLength)
                    maxLength = j-i+1;
            }
        }
        return maxLength;
    }

    // 滑动窗口[i,j)，时间复杂度O(n)
    public static int lengthOfLongestSubstring3(String s) {
        if(s==null) return 0;
        if(s.length()<=1) return s.length();

        int i=0, j=0;
        int answer = 0;
        int n = s.length();
        Set<Character> set = new HashSet<>();
        while (i<n && j<n) {
            if(!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                answer = Math.max(answer, j-i);
            }else {
                set.remove(s.charAt(i++));
            }
        }
        return answer;
    }

    public static int oneNum(int n) {
        int count = 0;
        while (n>0) {
            if((n&1) == 1)
                count++;
            n = n >> 1;
        }
        return count;
    }

    public static int oneNum2(int n) {
        int count = 0;
        for(;n>0;count++) {
            n = n & (n-1);
        }
        return  count;
    }
}
