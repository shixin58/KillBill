package com.max.client;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * <p>Created by shixin on 2018/9/7.
 */
public class QueueClient {

    public static void main(String[] args) {

        testPriorityQueue();

        testStack();
    }

    private static void testPriorityQueue() {
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
        System.out.println("priorityQueue.poll() "+priorityQueue.poll());
        // 多线程同步
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue();
        priorityBlockingQueue.add(4);
        priorityBlockingQueue.add(2);
        priorityBlockingQueue.add(6);
        System.out.println("priorityBlockingQueue.remove() "+priorityBlockingQueue.remove());
    }

    private static void testStack() {
        Stack<String> stack = new Stack<>();
        stack.push("cloud");
        stack.push("java");
        stack.push("algorithm");
        while (!stack.empty()) {
            System.out.println(stack.peek());
            stack.pop();
        }
    }
}
