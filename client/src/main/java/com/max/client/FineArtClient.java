package com.max.client;

import com.max.client.annotation.Curriculum;
import com.max.client.annotation.Stone;
import com.max.client.annotation.Superman;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * <p>Created by shixin on 2018/9/1.
 */
public class FineArtClient {

    public static void main(String[] args) {
        try {
            Class<?> cls = Class.forName("com.max.client.bean.FineArt");

            // 运行时通过反射获取注解
            Curriculum curriculum = cls.getAnnotation(Curriculum.class);
            System.out.println(curriculum.value()+"-"+curriculum.age());

            // 获得方法的某个注解AccessibleObject.getAnnotation(Class)，类比Constructor和Field
            // 判断注解是否存在Executable#isAnnotationPresent
            Stone stone = cls.getMethod("doSculpture").getAnnotation(Stone.class);
            System.out.println(stone.toString());
            System.out.println(Stone.class.getCanonicalName());

            // 获取单个方法的全部注解AccessibleObject.getAnnotations()，类比Constructor和Field
            Method method = cls.getMethod("doArchitecture", String.class);
            Annotation[] annotations = method.getAnnotations();
            System.out.println(Arrays.toString(annotations));

            // 获取单个方法含有的所有参数的全部注解
            Superman superman = (Superman) method.getParameterAnnotations()[0][0];
            System.out.println(superman.height());

            // 获取单个方法含有的全部参数Class
            Class<?> parameterType = method.getParameterTypes()[0];
            System.out.println("getCanonicalName "+parameterType.getCanonicalName());

            // 修饰符Method.getModifiers(), Modifier
            System.out.println("isPublic "+Modifier.isPublic(method.getModifiers()));

            // 获取父类信息
            System.out.println("getSuperclass "+cls.getSuperclass().getCanonicalName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
