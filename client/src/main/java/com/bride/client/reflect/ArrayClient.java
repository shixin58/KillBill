package com.bride.client.reflect;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * <p>Created by shixin on 2019/3/18.
 */
public class ArrayClient {
    public static void main(String[] args) {
        // 反射Array
        Class<?> stringClass = null;
        try {
            stringClass = Class.forName("java.lang.String");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object object2 = Array.newInstance(stringClass, 5);
        Array.set(object2, 0, "Art");
        Array.set(object2, 1, "Math");
        Array.set(object2, 2, "Music");
        Array.set(object2, 3, "Sports");
        Array.set(object2, 4, "Language");
        System.out.println(Array.get(object2, 1));

        // 数组也是Object子类，可调用Object所有方法
        // String.valueOf(obj) -> obj.toString()
        System.out.println(Arrays.toString((Object[]) object2));
    }
}
