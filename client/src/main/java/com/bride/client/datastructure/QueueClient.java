package com.bride.client.datastructure;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
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
        simulateArrayDeque();
    }

    // 案例1 - PriorityQueue
    // PriorityQueue#offer()/poll()默认实现小顶堆，利用Comparator实现大顶堆
    public static void testPriorityQueue() {
        System.out.println("=== PriorityQueue ===");
        // ClassCastException: com.bride.client.reflect.Human cannot be cast to java.lang.Comparable
        /*PriorityQueue<Human> humanPriorityQueue = new PriorityQueue<>();
        humanPriorityQueue.offer(new Human("Max", 20, 10000));
        humanPriorityQueue.offer(new Human("Victor", 30, 20000));
        System.out.print("humanPriorityQueue.poll() "+humanPriorityQueue.poll().getName());*/

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

    // 案例2 - LinkedList
    // LinkedList实现双向链式队列
    public static void testLinkedList() {
        System.out.println("=== LinkedList ===");
        Deque<Integer> linkedList = new LinkedList<>();
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

    // 案例3 - ArrayDeque
    // 实际应用：OkHttp中Dispatcher#runningSyncCalls/runningAsyncCalls/readyAsyncCalls，add()/remove()用synchronized保证线程安全
    // 原理：双向顺序队列，内部Object数组默认容量16，若判定容量已满，双倍扩容。
    // head(peekFirst)指向第一个元素位置，tail(peekLast)指向最后元素下一个位置。
    // 插入null会抛出NullPointer；not thread-safe, 用作栈比Stack更快，用作链表比LinkedList更快
    public static void testArrayDeque() {
        System.out.println("=== ArrayDeque ===");
        Deque<Integer> arrayDeque = new ArrayDeque<>(100);
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

    // 案例4 - 模拟ArrayDeque#addFirst(E)/addLast(E)实现
    static void simulateArrayDeque() {
        int pointer = 0;
        String[] values = {"copper", "iron", "gold", "silver"};
        System.out.println(values[pointer = (pointer - 1) & (values.length - 1)]);// 双端队列
        System.out.println(((0-1) & 15) + " " + ((15+1) & 15));

        pointer = 0;
        System.out.println(values[pointer++]);
        pointer = 0;
        System.out.println(values[++pointer]);
        pointer = 0;
        System.out.println(values[pointer += 1]);// 相当于++i
    }
}
