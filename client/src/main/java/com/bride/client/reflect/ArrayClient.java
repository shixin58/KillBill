package com.bride.client.reflect;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

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

        String[] names = (String[]) Array.newInstance(String.class, 6);
        names[0] = "New York City";
        names[1] = "Los Angeles";
        names[2] = "Mexico City";
        System.out.println(Arrays.toString(names));

        polymorphic();

        copy();

        order();
    }

    public static void polymorphic() {
        Object[] objects0 = {"Victor", "Jacob", "Max"};// Object数组
        Object[] objects = new String[]{"Victor", "Jacob", "Max"};// String 数组
        System.out.println(objects0+" "+objects);
        try {
            objects[0] = Integer.valueOf(3);
        } catch (Exception e) {
            System.err.println(e);
        }

        Individual[] individuals = new Human[2];
        try {
            individuals[0] = new Animal();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void copy() {
        // java.lang.ArrayStoreException
        Integer[] integers = {3, 5, 8, 7};
        Integer[] integers1 = new Integer[integers.length * 2];
        System.arraycopy(integers, 0, integers1, 0, integers.length);
        System.out.printf("integers = %s, integers1 = %s\n", Arrays.toString(integers), Arrays.toString(integers1));

        String[][] animals = {{"crow", "pigeon"}, {"swordfish"}};
        Object[][] objects = {{"crow", "pigeon"}, {"swordfish", "squid"}};
        System.out.println("deepEquals: "+Arrays.deepEquals(animals, objects));
    }

    public static void order() {
        Integer[] integers = {3, 1, 5, 4};
        // 泛型
        Arrays.sort(integers, Collections.reverseOrder());
        Arrays.sort(integers);
        System.out.println(Arrays.toString(integers));
        // 未找到，返回位置为 -(插入点) -1
        System.out.printf("position of 3 = %d, position of 8 = %d\n", Arrays.binarySearch(integers, 3), Arrays.binarySearch(integers, 2));

        String[] strings = {"banana", "apple", "orange"};
        Arrays.sort(strings, String.CASE_INSENSITIVE_ORDER);
        System.out.println(Arrays.toString(strings));
    }
}
