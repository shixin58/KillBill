package com.bride.client.language;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * <p>Created by shixin on 2019-05-05.
 */
public class GenericsInference {

    static void setList(List<? extends CharSequence> list) {
        for (CharSequence charSequence : list) {
            System.out.print(charSequence+", ");
        }
        System.out.println();
    }

    static void setQueue(Queue<String> queue) {

    }

    static void setCounter(Integer i) {
        int receiver = i.intValue();// 拆箱
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("ABCD".split(""));
        setList(list);

        Queue<String> queue = New.queue();
        setQueue(New.<String>queue());

        setCounter(Integer.valueOf(1));// 装箱
    }
}
