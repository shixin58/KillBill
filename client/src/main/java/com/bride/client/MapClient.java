package com.bride.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map子类 demo
 * <p>Created by shixin on 2018/4/23.
 */
public class MapClient {

    public static void main(String[] args) {

        testHashMap();

        testHashtable();

        testLinkedHashMap();
    }

    // put通过key计算hash值，将其映射到对象引用
    // get通过key计算hash值，通过其找到对象引用
    // 由数组、链表共同完成。数组内存地址连续，大小length固定，查询时间复杂度O(1)；链表不连续，查询时间复杂度O(n), 插入、删除快
    private static void testHashMap() {
        Map<String, String> map = new HashMap<>();
        map.put("cat", "猫");
        map.put("dog", "狗");
        map.put("wolf", "狼");
        map.put("fox", "狐狸");
        Set<Map.Entry<String, String>> set = map.entrySet();
        for(Map.Entry<String, String> entry : set) {
            System.out.println("HashMap entrySet "+entry.getKey()+" -> "+entry.getValue());
        }
        Set<String> keys = map.keySet();
        for(String key : keys) {
            System.out.println("HashMap key "+key);
        }
        Collection<String> values = map.values();
        for(String value : values) {
            System.out.println("HashMap value "+value);
        }
        System.out.println("HashMap get "+map.get("cat"));
    }

    // 多线程访问，通过synchronized加锁保证线程安全
    private static void testHashtable() {
        Map<String, String> map = new Hashtable<>();
        map.put("cat", "猫");
        map.put("dog", "狗");
        map.put("wolf", "狼");
        map.put("fox", "狐狸");
        Set<Map.Entry<String, String>> set = map.entrySet();
        for(Map.Entry<String, String> entry : set) {
            System.out.println("Hashtable entrySet "+entry.getKey()+" -> "+entry.getValue());
        }
    }

    private static void testLinkedHashMap() {
        MyLruCache lruCache = new MyLruCache(5);
        lruCache.put("A", "Apple");
        lruCache.put("B", "Banana");
        lruCache.put("C", "Cat");
        lruCache.put("D", "Dog");
        lruCache.put("E", "Egg");
        System.out.println("C->"+lruCache.get("C"));
        System.out.println(lruCache.toString());
        lruCache.put("F", "Fourth");
        System.out.println(lruCache.toString());
    }

    // LRU缓存算法实现
    private static class MyLruCache extends LinkedHashMap<String,String> {
        private final int CACHE_MAX_SIZE;
        public MyLruCache(int cacheSize) {
            super((int) Math.ceil(cacheSize/0.75f) + 1, 0.75f, true);
            CACHE_MAX_SIZE = cacheSize;
        }

        @Override
        protected boolean removeEldestEntry(Entry eldest) {
            return size()>CACHE_MAX_SIZE;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            Set<Entry<String, String>> set = entrySet();
            Iterator<Entry<String, String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Entry<String, String> entry = iterator.next();
                stringBuilder.append(entry.getKey())
                        .append("->")
                        .append(entry.getValue())
                        .append(", ");
            }
            return stringBuilder.toString();
        }
    }
}
