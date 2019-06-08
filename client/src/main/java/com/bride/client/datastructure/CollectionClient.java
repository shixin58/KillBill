package com.bride.client.datastructure;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * <p>Created by shixin on 2019-06-06.
 */
public class CollectionClient {

    public static void main(String[] args) {
        collections();
    }

    public static void collections() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        Collections.addAll(linkedList, 3, 1, 5, 4);
        System.out.printf("initialize: %s\n", linkedList.toString());

        Collections.sort(linkedList);// 元素须实现Comparable接口
        System.out.println(linkedList.toString());

        Random random = new Random(47);
        Collections.shuffle(linkedList, random);
        System.out.println(linkedList);

        Integer[] ints = linkedList.toArray(new Integer[linkedList.size()]);
        System.out.println(Arrays.toString(ints));
    }
}
