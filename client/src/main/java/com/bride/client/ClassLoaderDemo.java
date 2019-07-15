package com.bride.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * <p>Created by shixin on 2019-05-21.
 */
public class ClassLoaderDemo {

    public static void main(String[] args) {
        diskClassLoader();

//        networkClassLoader();

//        networkJarLoader(ClassLoaderDemo.class.getClassLoader());

        classCast();
    }

    public static void diskClassLoader() {
        CustomClassLoader classLoader = new CustomClassLoader("/Users/shixin/CreativeProjects/HelloJava/classes");
        try {
            Class<?> cls = classLoader.loadClass("com.max.creative.ClassLoaderClient");
            Method method = cls.getMethod("main", String[].class);
            String[] params = new String[]{"Victor", "Max", "Jacob"};
            method.invoke(null, (Object) params);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void networkClassLoader() {
        NetworkClassLoader classLoader = new NetworkClassLoader("http://47.91.249.201:8080/classes");
        try {
            Class<?> cls = classLoader.loadClass("com.max.creative.ClassLoaderClient");
            Method method = cls.getMethod("main", String[].class);
            String[] params = new String[]{"Victor", "Max", "Jacob"};
            method.invoke(null, (Object) params);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void networkJarLoader(ClassLoader parent) {
        try {
            URL[] urls = {new URL("http://47.91.249.201:8080/libs/hellojava.jar")};
            URLClassLoader classLoader = new URLClassLoader(urls, parent);
            Class<?> cls = classLoader.loadClass("com.max.creative.ClassLoaderClient");
            Method method = cls.getMethod("main", String[].class);
            String[] params = new String[]{"Victor", "Max", "Jacob"};
            method.invoke(null, (Object) params);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void classCast() {
        CustomClassLoader classLoader1 = new CustomClassLoader("/Users/shixin/CreativeProjects/HelloJava/classes");
        CustomClassLoader classLoader2 = new CustomClassLoader("/Users/shixin/CreativeProjects/HelloJava/classes");
        try {
            System.out.println(classLoader1.loadClass("com.max.creative.ClassLoaderClient")
                    .equals(classLoader2.loadClass("com.max.creative.ClassLoaderClient")));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
