package com.bride.client.datastructure;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * <p>Created by shixin on 2019-06-06.
 */
public class CollectionClient {

    public static void main(String[] args) {
        collections();
        anonymous();
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
        Integer[] integers = (Integer[]) linkedList.toArray();

    }

    public static void anonymous() {
        List<String> unmodifiableList = Collections.unmodifiableList(Arrays.asList("Victor", "Simon", "Max", "Jacob"));
        for (String s: unmodifiableList) {
            System.out.print(s+", ");
        }
        List<String> synchronizedList = Collections.synchronizedList(Arrays.asList("Mouse", "Rat", "Hamster"));
        List<String> singletonList = Collections.singletonList("Google");
    }
}
