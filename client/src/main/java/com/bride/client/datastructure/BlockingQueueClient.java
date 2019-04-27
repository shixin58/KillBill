package com.bride.client.datastructure;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * <p>Created by shixin on 2019/3/18.
 */
public class BlockingQueueClient {

    public static void main(String[] args) {
//        testArrayBlockingQueue();
        testPriorityBlockingQueue();
    }

    private static void testArrayBlockingQueue() {
        System.out.println("=== ArrayBlockingDeque ===");
        final ArrayBlockingQueue<Double> queue = new ArrayBlockingQueue<>(100);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while(true) {
                        System.out.println("take "+queue.take());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                int count = 10;
                while (count-- > 0){
                    try {
                        Thread.sleep(1000L);
                        double d = 8.9+count;
                        System.out.println("offer "+d);
                        queue.offer(d, 2, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                thread.interrupt();
            }
        }.start();
    }

    public static void testPriorityBlockingQueue() {
        // 求最小值, 多线程同步
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>(5);
        priorityBlockingQueue.add(4);
        priorityBlockingQueue.add(2);
        priorityBlockingQueue.add(6);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    for (int i=0; i<10; i++) {
                        Thread.sleep(2000L);
                        priorityBlockingQueue.offer(i);
                        System.out.println("offer "+i+"; size = "+priorityBlockingQueue.size());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        try {
            while (true) {
                Integer integer = priorityBlockingQueue.poll(5, TimeUnit.SECONDS);
                if (integer == null)
                    break;
                System.out.println("PriorityBlockingQueue#poll "+integer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
