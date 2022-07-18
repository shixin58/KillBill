package com.bride.client.datastructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * Map子类 demo
 * <p>Created by shixin on 2018/4/23.
 */
public class MapClient {

    public static void main(String[] args) {
        testHashMap();

        testHashtable();

        testTreeMap();
    }

    // put通过key计算hash值，将其映射到对象引用
    // get通过key计算hash值，通过其找到对象引用
    // 由数组、链表共同完成。数组内存地址连续，length固定，查询时间复杂度O(1)；链表不连续，查询时间复杂度O(n), 插入、删除快
    public static void testHashMap() {
        System.out.println("\n*** HashMap ***");
        Map<String, String> map = new HashMap<>();
        map.put("cat", "猫");
        map.put("dog", "狗");
        map.put("wolf", "狼");
        map.put("fox", "狐狸");
        map.put(null, "Empty!");
        map.put("plant", null);
        System.out.println(map);
        Map<String, String> subMap = new LinkedHashMap<>();
        subMap.put("plant", "rose");
        subMap.put("wolf", "smart");
        subMap.put("mouse", "鼠标");
        map.putAll(subMap);
        System.out.println(map);

        System.out.println(map.containsKey(null) +" "+ map.containsKey("plant"));
        System.out.println(map.get("fish")+" "+map.get(null)+" "+map.get("plant"));// 返回null而不是异常

        Set<Map.Entry<String, String>> set = map.entrySet();
        for(Map.Entry<String, String> entry : set) {
            System.out.print(entry.getKey()+" -> "+entry.getValue()+", ");
        }
        System.out.println();

        Set<String> keys = map.keySet();
        for(String key : keys) {
            System.out.print(key+", ");
        }
        System.out.println();

        Collection<String> values = map.values();
        for(String value : values) {
            System.out.print(value+", ");
        }
        System.out.println();
    }

    // Hashtable对put()/get()方法synchronized加锁，且全表锁，效率低；不允许null key和null value。
    public static void testHashtable() {
        System.out.println("\n*** Hashtable ***");
        Map<String, String> map = new Hashtable<>();
        map.put("cat", "猫");
        map.put("dog", "狗");
        map.put("wolf", "狼");
        map.put("fox", "狐狸");
        Set<Map.Entry<String, String>> set = map.entrySet();
        for(Map.Entry<String, String> entry : set) {
            System.out.print(entry.getKey()+" -> "+entry.getValue()+", ");
        }
        System.out.println();
    }

    public static void testTreeMap() {
        System.out.println("\n*** TreeMap ***");
        NavigableMap<Integer, String> map = new TreeMap<>();
        map.put(2, "rabbit");
        map.put(1, "turtle");
        map.put(3, "fox");
        System.out.println(map);
    }
}
