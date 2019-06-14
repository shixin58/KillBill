package com.bride.client.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * 反射reflect
 * <p>Created by shixin on 2018/9/1.
 */
public class ReflectClient {

    public static void main(String[] args) {
        try {
            // 获取class对象
            Class<?> cl = Human.class;
            // 创建对象
            Constructor constructor = cl.getConstructor(String.class, int.class, double.class);
            System.out.println(constructor.toString());
            Object object = constructor.newInstance("Max", 29, 100000);
            // 创建空参构造
            Object object1 = cl.newInstance();

            // 获得Method
            Method method = cl.getMethod("getName");
            Method method1 = cl.getMethod("setName", String.class);
            System.out.println(method.invoke(object));
            method1.invoke(object, "Jacob");
            System.out.println(method.invoke(object));

            // 获得方法返回值
            Type returnType = method.getGenericReturnType();
            Class returnCls = method.getReturnType();
            // class java.lang.String
            System.out.println("returnType "+returnType.toString());
            // java.lang.String
            System.out.println("returnCls "+returnCls.getName());

            Method methodHobbies = cl.getMethod("getHobbies");
            Type returnTypeHobbies = methodHobbies.getGenericReturnType();
            // java.util.List<java.lang.String>
            System.out.println("returnTypeHobbies "+returnTypeHobbies.toString());
            // interface java.util.List
            System.out.println("returnClsHobbies "+methodHobbies.getReturnType().toString());

            // 获得Field
            Field field = cl.getField("performance");
            System.out.println(field.toString()+" "+field.getInt(object));

            if(cl.isInstance(object)) {
                // 等同于instanceof
            }

            // 调用私有方法和域
            Method method2 = cl.getDeclaredMethod("getSalary");
            method2.setAccessible(true);
            System.out.println(method2.toString()+" "+method2.invoke(object));
            Field field1 = cl.getDeclaredField("salary");
            field1.setAccessible(true);
            System.out.println(field1.toString()+" "+field1.getDouble(object));
            // 子类覆盖的方法Method和父类相同
            Individual.class.getMethod("born").invoke(object);

            // 遍历类中所有方法
            Method[] methods = cl.getDeclaredMethods();
            for(int i=0;i<methods.length;i++) {
                System.out.print(methods[i].getName()+", ");
            }
            System.out.println();

            // 遍历类中所有域
            Field[] fields = cl.getDeclaredFields();
            for (Field f : fields) {
                System.out.print(f.getName()+", ");
            }
            System.out.println();

            new Human.Factory().create();

            System.out.println(Arrays.toString(Human.class.getClasses()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
