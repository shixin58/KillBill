package com.victor.demon.utils;

import android.text.TextUtils;
import android.util.SparseArray;

import java.util.List;

/**
 * <p>Created by shixin on 2018/5/11.
 */
public class SearchUtils {

    public String getAllHighlightWords(String title, List<String> keys) {
        if(keys==null || keys.isEmpty()) {
            return "";
        }
        if(TextUtils.isEmpty(title)) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        // key为position，value为关键词
        SparseArray<String> sparseArray = new SparseArray<>();
        String lowSource = title.toLowerCase();
        for (String keyWord : keys) {
            String lowKeyWord = keyWord.toLowerCase();
            if (lowSource.contains(lowKeyWord)) {
                int length = lowKeyWord.length();
                int start = lowSource.indexOf(lowKeyWord, 0);
                int end = start + lowKeyWord.length();
                while (start != -1) {
                    // 过滤掉同一个位置开始但更短的关键词
                    if(TextUtils.isEmpty(sparseArray.get(start)) || sparseArray.get(start).length()<(end-start)) {
                        sparseArray.put(start, title.substring(start, end));
                    }
                    start = lowSource.indexOf(lowKeyWord, start + length);
                    end = start + length;
                }
            }
        }
        // 过滤掉后开始早结束的关键词
        for(int i=1;i<sparseArray.size();i++) {
            if(sparseArray.keyAt(i)+sparseArray.valueAt(i).length() <=
                    sparseArray.keyAt(i-1)+sparseArray.valueAt(i-1).length()) {
                sparseArray.removeAt(i);
                i--;
            }
        }
        // 将关键词逗号分割拼接成字符串
        for(int i=0;i<sparseArray.size();i++) {
            stringBuilder.append(sparseArray.valueAt(i)).append(",");
        }
        return stringBuilder.length()>0?stringBuilder.deleteCharAt(stringBuilder.length()-1).toString():"";
    }
}
