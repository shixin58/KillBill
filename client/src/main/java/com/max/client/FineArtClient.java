package com.max.client;

import com.max.client.annotation.Curriculum;
import com.max.client.annotation.Stone;
import com.max.client.annotation.Superman;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * <p>Created by shixin on 2018/9/1.
 */
public class FineArtClient {

    public static void main(String[] args) {
        try {
            Class<?> cls = Class.forName("com.victor.demon.repository.FineArt");

            // 运行时通过反射获取注解
            Curriculum curriculum = cls.getAnnotation(Curriculum.class);
            System.out.println(curriculum.value()+"-"+curriculum.age());

            Stone stone = cls.getMethod("doSculpture").getAnnotation(Stone.class);
            System.out.println(stone.toString());
            System.out.println(Stone.class.getCanonicalName());

            // 获取方法注解
            Method method = cls.getMethod("doArchitecture", String.class);
            Annotation[] annotations = method.getAnnotations();
            System.out.println(Arrays.toString(annotations));

            // 获取参数注解
            Superman superman = (Superman) method.getParameterAnnotations()[0][0];
            System.out.println(superman.height());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
