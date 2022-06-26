package com.bride.client.datastructure;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 演示Collections和BitSet工具类用法
 * <p>Created by shixin on 2019-06-06.
 */
public class CollectionClient {

    public static void main(String[] args) {
        collections();
        anonymous();

        testBitSet();

        testHashSet();
    }

    public static void collections() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        // Java5语法糖Syntactic Sugar，变长参数varargs
        Collections.addAll(linkedList, 3, 1, 5, 4);
        System.out.printf("initialize: %s\n", linkedList.toString());

        Collections.sort(linkedList);// 元素须实现Comparable接口
        System.out.println("after sort: "+linkedList.toString());

        Random random = new Random(47);
        Collections.shuffle(linkedList, random);
        System.out.println("after shuffle: "+linkedList);

        Integer[] ints = linkedList.toArray(new Integer[0]);
        System.out.println(Arrays.toString(ints));

        // Java5 foreach遍历数组
        for (Object o:linkedList.toArray()) {
            System.out.print(o.toString()+", ");
        }
        System.out.println();
    }

    // anonymous匿名的/不具名的
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
        // 默认nbits=64
        BitSet bitSet = new BitSet();
        bitSet.set(0, true);
        bitSet.set(1, false);
        bitSet.set(2);// true
        bitSet.clear(3);// false
        bitSet.set(4, 8);// [4, 8)true
        String bits = bitSet.toString();
        System.out.println("bitSet: "+bits+"; length: "+bitSet.length()+"; size: "+bitSet.size());

        BitSet anotherBitSet = new BitSet();
        anotherBitSet.set(0, 3);
        anotherBitSet.clear(4, 7);// [4, 7)false
        anotherBitSet.set(7);
        System.out.println("anotherBitSet: "+anotherBitSet.toString()+"; length: "+anotherBitSet.length());

        // 交换swap
        bitSet.xor(anotherBitSet);
        anotherBitSet.xor(bitSet);
        bitSet.xor(anotherBitSet);
        System.out.println(bitSet.toString()+", "+anotherBitSet);

        // nbits取值仅2的幂Power of Two
        BitSet bigBitSet = new BitSet(65);
        System.out.println("bigBitSet.size(): "+bigBitSet.size()+", length: "+bigBitSet.length());
        bigBitSet.set(255);
        bigBitSet.set(256, false);// true才会扩大数组、增加length
        System.out.println(bigBitSet+", size(): "+bigBitSet.size()+", length: "+bigBitSet.length());
    }

    public static void testHashSet() {
        HashSet<Val<Integer>> set = new HashSet<>();
        set.add(new Val<>(1));
        set.add(new Val<>(2));
        set.add(new Val<>(3));
        set.add(new Val<>(4));
        int sum = set.stream()
            .map(Val::getValue)// method reference，代替x -> x.getValue()
            .reduce(Integer::sum)// (a, x) -> a + x
            .get();
        System.out.println("testHashSet "+sum);
    }

    public static class Val<T> {
        private T value;

        public Val(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
