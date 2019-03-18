package com.bride.client.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * 最长不重复子串
 * <p>Created by shixin on 2019/3/18.
 */
public class LengthOfLongestSubstringClient {
    public static void main(String[] args) {
        System.out.println("lengthOfLongestSubstring "+lengthOfLongestSubstring("acebhhbdjloium"));
        System.out.println("lengthOfLongestSubstring2 "+lengthOfLongestSubstring2("acebhhbdjloium"));
        System.out.println("lengthOfLongestSubstring3 "+lengthOfLongestSubstring3("acebhhbdjloium"));
    }

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
}
