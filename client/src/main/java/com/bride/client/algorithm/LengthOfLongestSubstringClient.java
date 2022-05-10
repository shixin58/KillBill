package com.bride.client.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * 求最长不重复子串longest unrepeatable substring
 * <p>Created by shixin on 2019/3/18.
 */
public class LengthOfLongestSubstringClient {
    public static void main(String[] args) {
        System.out.println("lengthOfLongestSubstring "+ maxLenOfUnrepeatableSub("acebhhbdjloium"));
        System.out.println("lengthOfLongestSubstring2 "+ maxLenOfUnrepeatableSubHash("acebhhbdjloium"));
        System.out.println("lengthOfLongestSubstring3 "+ maxLenOfUnrepeatableSubSlidingWin("acebhhbdjloium"));
    }

    // 1）indexOf查重
    public static int maxLenOfUnrepeatableSub(String s) {
        // 边界值处理
        if(s==null) return 0;
        if(s.length()<=1) return s.length();

        final int len = s.length();
        int maxLen = 1;
        // 求从i开始的最长不重复子串长度
        for(int i=0, j=0; i<len; i++) {
            for(j=Math.max(i+1, j); j<len; j++) {
                // 从[i, j]搜索s[j]，复杂度O(j-i+1)
                int idxJ = s.indexOf(s.charAt(j), i);
                if(idxJ < j) {// 若repeat，退出本次迭代，下次i=idxJ+1
                    i = idxJ;
                    // [idxJ+1, j]确定不重复
                    j++;
                    break;
                } else {// [i, j]子串不重复，更新maxLen
                    if(j-i+1 > maxLen)
                        maxLen = j-i+1;
                }
            }
        }
        return maxLen;
    }

    // 2）HashSet查重
    public static int maxLenOfUnrepeatableSubHash(String s) {
        if(s==null) return 0;
        if(s.length()<=1) return s.length();

        final int len = s.length();
        int maxLen = 1;
        // HashSet保存从i开始的最长不重复子串
        HashSet<Character> set = new HashSet<>();
        // 求从i开始的最长不重复子串长度
        for(int i=0; i<len && len-i>maxLen; i++) {
            set.clear();
            set.add(s.charAt(i));
            for(int j=i+1; j<len; j++) {
                // contains和charAt复杂度均O(1)
                if(!set.add(s.charAt(j)))
                    break;
                if(j-i+1 > maxLen)
                    maxLen = j-i+1;
            }
        }
        return maxLen;
    }

    // 3）滑动窗口[i,j)，时间复杂度O(n)
    public static int maxLenOfUnrepeatableSubSlidingWin(String s) {
        if(s==null) return 0;
        if(s.length()<=1) return s.length();

        final int len = s.length();
        int maxLen = 0;
        // HashSet保存不重复子串[i, j]
        Set<Character> set = new HashSet<>();
        int i=0, j=0;
        while (i<len && j<len) {
            if(!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                maxLen = Math.max(maxLen, j-i);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return maxLen;
    }
}
