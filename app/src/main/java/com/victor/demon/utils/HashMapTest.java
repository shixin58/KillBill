package com.victor.demon.utils;

import android.util.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * <p>Created by shixin on 2018/4/23.
 */
public class HashMapTest {

    public static void testCommonApi() {
        // put通过key计算hash值，将其映射到对象引用
        // get通过key计算hash值，通过其找到对象引用
        // 由数组、链表共同完成。数组内存地址连续，大小length固定，查询时间复杂度O(1)；链表不连续，查询时间复杂度O(n), 插入、删除快
        Map<String, String> map = new HashMap<>();
        map.put("cat", "猫");
        map.put("dog", "狗");
        map.put("wolf", "狼");
        map.put("fox", "狐狸");
        Set<Map.Entry<String, String>> set = map.entrySet();
        for(Map.Entry<String, String> entry : set) {
            Log.i("Victor", "HashMap entrySet "+entry.getKey()+" -> "+entry.getValue());
        }
        Set<String> keys = map.keySet();
        for(String key : keys) {
            Log.i("Victor", "HashMap key "+key);
        }
        Collection<String> values = map.values();
        for(String value : values) {
            Log.i("Victor", "HashMap value "+value);
        }
        Log.i("Victor", "HashMap get "+map.get("cat"));
    }

    public static void testHashtable() {
        // 多线程访问，通过synchronized加锁保证线程安全。
        Map<String, String> map = new Hashtable<>();
        map.put("cat", "猫");
        map.put("dog", "狗");
        map.put("wolf", "狼");
        map.put("fox", "狐狸");
        Set<Map.Entry<String, String>> set = map.entrySet();
        for(Map.Entry<String, String> entry : set) {
            Log.i("Victor", "Hashtable entrySet "+entry.getKey()+" -> "+entry.getValue());
        }
    }
}
