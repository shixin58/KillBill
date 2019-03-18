package com.bride.client.datastructure;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * <p>Created by shixin on 2019/3/18.
 */
public class BlockingQueueClient {

    public static void main(String[] args) {
        testArrayBlockingQueue();
        testPriorityBlockingQueue();
    }

    private static void testArrayBlockingQueue() {
        System.out.println("=== ArrayBlockingDeque ===");
        ArrayBlockingQueue<Double> queue = new ArrayBlockingQueue<>(100);
        queue.offer(1.5);
        queue.offer(-77.4);
        queue.offer(9.6);
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }

    public static void testPriorityBlockingQueue() {
        // 多线程同步
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
        priorityBlockingQueue.add(4);
        priorityBlockingQueue.add(2);
        priorityBlockingQueue.add(6);
        // 求最小值
        System.out.println("priorityBlockingQueue.remove() "+priorityBlockingQueue.remove());
    }
}
