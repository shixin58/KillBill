package com.bride.client;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Queue子类 demo
 * <p>Created by shixin on 2018/9/7.
 */
public class QueueClient {

    public static void main(String[] args) {

        testPriorityQueue();

        testStack();

        testArrayBlockingQueue();

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

        // 多线程同步
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue();
        priorityBlockingQueue.add(4);
        priorityBlockingQueue.add(2);
        priorityBlockingQueue.add(6);
        // 求最小值
        System.out.println("priorityBlockingQueue.remove() "+priorityBlockingQueue.remove());
    }

    private static void testStack() {
        System.out.println("=== Stack ===");
        Stack<String> stack = new Stack<>();
        stack.push("cloud");
        stack.push("java");
        stack.push("algorithm");
        while (!stack.empty()) {
            System.out.println(stack.peek());
            stack.pop();
        }
    }

    private static void testArrayBlockingQueue() {
        System.out.println("=== ArrayBlockingDeque ===");
        Queue<Double> queue = new ArrayBlockingQueue<>(100);
        queue.offer(1.5);
        queue.offer(-77.4);
        queue.offer(9.6);
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
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
