package com.victor.demon;

import com.victor.demon.repository.Human;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * <p>Created by shixin on 2018/9/1.
 */
public class HumanClient {

    public static void main(String[] args) {
        try {
            // 获取class对象
            Class<?> cl = Human.class;
            // 创建对象
            Constructor constructor = cl.getConstructor(String.class, int.class, double.class);
            Object object = constructor.newInstance("Max", 29, 100000);
            // 创建空参构造
            Object object1 = cl.newInstance();

            // 获得Method
            Method method = cl.getMethod("getName");
            Method method1 = cl.getMethod("setName", String.class);
            // 获得Field
            Field field = cl.getField("performance");
            System.out.println(method.invoke(object));
            method1.invoke(object, "Jacob");
            System.out.println(method.invoke(object));
            System.out.println(""+field.getInt(object));
            if(cl.isInstance(object)) {
                // 等同于instanceof
            }

            // 调用私有方法和域
            Method method2 = cl.getDeclaredMethod("getSalary");
            method2.setAccessible(true);
            System.out.println(method2.invoke(object));
            Field field1 = cl.getDeclaredField("salary");
            field1.setAccessible(true);
            System.out.println(field1.getDouble(object));
            Method[] methods = cl.getDeclaredMethods();
            for(int i=0;i<methods.length;i++) {
                System.out.print(methods[i].getName()+", ");
            }
            System.out.println();
            Field[] fields = cl.getDeclaredFields();
            for (int i=0;i<fields.length;i++) {
                System.out.print(fields[i].getName()+", ");
            }
            System.out.println();

            // 反射Array
            Class<?> stringClass = Class.forName("java.lang.String");
            Object object2 = Array.newInstance(stringClass, 5);
            Array.set(object2, 0, "Art");
            Array.set(object2, 1, "Math");
            Array.set(object2, 2, "Music");
            Array.set(object2, 3, "Sports");
            Array.set(object2, 4, "Language");
            System.out.println(Array.get(object2, 1));
            System.out.println(Arrays.toString((Object[]) object2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
