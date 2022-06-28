package com.bride.baselib.lru;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

// LRU缓存算法实现
public class MyLruCache extends LinkedHashMap<String, String> {
    private final int CACHE_MAX_SIZE;

    MyLruCache(int cacheSize) {
        super((int) Math.ceil(cacheSize/0.75f) + 1, 0.75f, true);
        CACHE_MAX_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Entry eldest) {
        return size() > CACHE_MAX_SIZE;
    }

    @Override
    public String toString() {
        // 遍历LinkedHashMap，超出上限末位淘汰
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

    public static void main(String[] args) {
        System.out.println("\n*** LinkedHashMap ***");
        MyLruCache lruCache = new MyLruCache(5);
        lruCache.put("A", "Apple");
        lruCache.put("B", "Banana");
        lruCache.put("C", "Cat");
        lruCache.put("D", "Dog");
        lruCache.put("E", "Egg");
        System.out.println(lruCache.toString());

        lruCache.get("C");
        System.out.println(lruCache.toString());

        lruCache.get("B");
        System.out.println(lruCache.toString());

        lruCache.put("F", "Fourth");
        System.out.println(lruCache.toString());
    }
}
