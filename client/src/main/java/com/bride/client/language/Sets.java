package com.bride.client.language;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>Created by shixin on 2019-05-05.
 */
public class Sets {

    public static <T> Set<T> union(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }

    public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }

    public static <T> Set<T> difference(Set<T> superset, Set<T> subset) {
        Set<T> result = new HashSet<>(superset);
        result.removeAll(subset);
        return result;
    }

    public static <T> Set<T> complement(Set<T> a, Set<T> b) {
        return difference(union(a, b), intersection(a, b));
    }

    public static void main(String[] args) {
        methodSet(Object.class);
        interfaces(Set.class);
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
