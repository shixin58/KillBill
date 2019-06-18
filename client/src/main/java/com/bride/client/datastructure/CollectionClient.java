package com.bride.client.datastructure;

import java.util.Arrays;
import java.util.BitSet;
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
        testBitSet();
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
        for (Object o:linkedList.toArray()) {
            System.out.print(o.toString());
        }
        System.out.println();
    }

    public static void anonymous() {
        List<String> unmodifiableList = Collections.unmodifiableList(Arrays.asList("Victor", "Simon", "Max", "Jacob"));
        for (String s: unmodifiableList) {
            System.out.print(s+", ");
        }
        List<String> synchronizedList = Collections.synchronizedList(Arrays.asList("Mouse", "Rat", "Hamster"));
        List<String> singletonList = Collections.singletonList("Google");
        List<String> emptyList = Collections.emptyList();
    }

    public static void testBitSet() {
        System.out.println("\n--- testBitSet ---");
        BitSet bitSet = new BitSet();
        bitSet.set(0, true);
        bitSet.set(1, false);
        bitSet.set(2);
        bitSet.clear(3);
        bitSet.set(4, 8);
        String bits = bitSet.toString();
        System.out.println("bitSet: "+bits+"; length: "+bitSet.length()+"; size: "+bitSet.size());

        BitSet anotherBitSet = new BitSet();
        anotherBitSet.set(0, 3);
        anotherBitSet.clear(4, 7);
        anotherBitSet.set(7);
        System.out.println("anotherBitSet: "+anotherBitSet.toString()+"; length: "+anotherBitSet.length());

        bitSet.xor(anotherBitSet);
        anotherBitSet.xor(bitSet);
        bitSet.xor(anotherBitSet);
        System.out.println(bitSet.toString()+", "+anotherBitSet);

        BitSet bigBitSet = new BitSet(65);
        System.out.println("bigBitSet.size(): "+bigBitSet.size()+", length: "+bigBitSet.length());
        bigBitSet.set(255);
        bigBitSet.set(256, false);
        System.out.println(bigBitSet+", size(): "+bigBitSet.size()+", length: "+bigBitSet.length());
    }
}
