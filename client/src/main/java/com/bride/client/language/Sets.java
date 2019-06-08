package com.bride.client.language;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>Created by shixin on 2019-05-05.
 */
public class Sets {

    public static void linkedHashSet() {
        Collection<String> collection = new LinkedList<>();
        collection.add("Watermelon");
        collection.add("Pineapple");
        collection.add("Grapefruit");
        LinkedHashSet<String> set = new LinkedHashSet<>(collection);
        set.add("Banana");
        set.add("Apple");
        set.add("Orange");
        System.out.println(set);
    }

    public static void treeSet() {
        NavigableSet<String> set = new TreeSet<>();
        Collections.addAll(set, "Dog", "Cat", "Fox");
        System.out.println(set);
    }

    // 并集
    public static <T> Set<T> union(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }

    // 交集。保留A和B都有的元素
    public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }

    // 相对补集. A中B的相对补集
    public static <T> Set<T> difference(Set<T> superset, Set<T> subset) {
        Set<T> result = new HashSet<>(superset);
        result.removeAll(subset);
        return result;
    }

    // 补集。A和B的交集的补集
    public static <T> Set<T> complement(Set<T> a, Set<T> b) {
        return difference(union(a, b), intersection(a, b));
    }

    public static void main(String[] args) {
        methodSet(Object.class);
        interfaces(Set.class);
        linkedHashSet();
        treeSet();
    }

    static void methodSet(Class<?> type) {
        Set<String> set = new TreeSet<>();
        // public方法
        for (Method method : type.getMethods()) {
            set.add(method.getName());
        }
        System.out.println(set);
    }

    static void interfaces(Class<?> type) {
        List<String> list = new ArrayList<>();
        // 直接实现的接口
        // [Collection]
        for (Class<?> cls : type.getInterfaces()) {
            list.add(cls.getSimpleName());
        }
        System.out.println(list);
    }
}
