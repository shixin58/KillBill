package com.bride.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>Created by shixin on 2019-06-04.
 */
public class GenericsClient {

    public static void main(String[] args) {
        type();
        erase();
    }

    public static void type() {
        Class cls1 =  new ArrayList<Integer>().getClass();
        Class cls2 = new ArrayList<String>().getClass();
        System.out.println(cls1 == cls2);
    }

    // 定义或实例化时类型擦除，替换为Object
    public static void erase() {
        List<String> list = new LinkedList<>();
        list.add("Apple");
        list.add("Banana");
        try {
            Method method = list.getClass().getMethod("add", Object.class);
            method.invoke(list, 11);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        for (Object o:list) {
            System.out.print(o+", ");
        }
        System.out.println();
    }
}
