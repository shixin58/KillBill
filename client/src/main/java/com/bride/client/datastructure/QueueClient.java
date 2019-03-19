package com.bride.client.datastructure;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Queue子类 demo
 * <p>Created by shixin on 2018/9/7.
 */
public class QueueClient {

    public static void main(String[] args) {

        testPriorityQueue();

        testLinkedList();

        testArrayDeque();
    }

    private static void testPriorityQueue() {
        System.out.println("=== PriorityQueue ===");
        // ClassCastException: com.bride.client.reflect.Human cannot be cast to java.lang.Comparable
        /*PriorityQueue<Human> humanPriorityQueue = new PriorityQueue<>();
        humanPriorityQueue.offer(new Human("Max", 20, 10000));
        humanPriorityQueue.offer(new Human("Victor", 30, 20000));
        System.out.print("humanPriorityQueue.poll() "+humanPriorityQueue.poll().getName());*/

        // 默认小顶堆，利用Comparator实现大顶堆
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(10, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        priorityQueue.offer(5);
        priorityQueue.offer(8);
        priorityQueue.offer(3);
        // 求最大值
        System.out.println("priorityQueue.poll() "+priorityQueue.poll());
    }

    // 双向链式队列
    private static void testLinkedList() {
        System.out.println("=== LinkedList ===");
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.addLast(1);
        linkedList.addLast(3);
        linkedList.addLast(-5);
        while (!linkedList.isEmpty()) {
            System.out.print(linkedList.removeFirst()+", ");
        }
        System.out.println();

        linkedList.addFirst(7);
        linkedList.addFirst(9);
        linkedList.addFirst(3);
        while (!linkedList.isEmpty()) {
            System.out.print(linkedList.removeLast()+", ");
        }
        System.out.println();
    }

    // 双向顺序队列
    private static void testArrayDeque() {
        System.out.println("=== ArrayDeque ===");
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>(100);
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        while (!arrayDeque.isEmpty()) {
            System.out.print(arrayDeque.removeFirst()+", ");
        }
        System.out.println();

        arrayDeque.addFirst(5);
        arrayDeque.addFirst(8);
        arrayDeque.addFirst(3);
        while (!arrayDeque.isEmpty()) {
            System.out.print(arrayDeque.removeLast()+", ");
        }
        System.out.println();
    }
}
