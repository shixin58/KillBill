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
        // 默认小顶堆，利用Comparator实现大顶堆
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(11, new Comparator<Integer>() {
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
            System.out.println(linkedList.removeFirst());
        }

        linkedList.addFirst(7);
        linkedList.addFirst(9);
        linkedList.addFirst(3);
        while (!linkedList.isEmpty()) {
            System.out.println(linkedList.removeLast());
        }
    }

    // 双向顺序队列
    private static void testArrayDeque() {
        System.out.println("=== ArrayDeque ===");
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>(100);
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        while (!arrayDeque.isEmpty()) {
            System.out.println(arrayDeque.removeFirst());
        }
    }
}
