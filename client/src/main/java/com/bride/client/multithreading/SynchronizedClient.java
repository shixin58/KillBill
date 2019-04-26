package com.bride.client.multithreading;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 每个对象有且仅有1个同步锁，不同线程对同步锁的访问是互斥的。
 * 1、synchronized同步锁，可重入锁，保证原子性
 * <p>2、wait/notify等待唤醒
 * <p>Created by shixin on 2019/3/20.
 */
public class SynchronizedClient {

    private static final Object object = new Object();
    private static boolean condition = false;

    public static void main(String[] args) {

//        testSynchronized();

        testWaitNotify();
    }

    public static void testSynchronized() {
        final SynchronizedClient synchronizedClient = new SynchronizedClient();
        Executor executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                synchronizedClient.printA();
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SynchronizedClient.printC();
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                synchronizedClient.printB();
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SynchronizedClient.printD();
            }
        });
    }

    public synchronized void printA() {
        System.out.println("A start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("A end "+Thread.currentThread().getName());
    }

    public synchronized void printB() {
        System.out.println("B start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("B end "+Thread.currentThread().getName());
    }

    public synchronized static void printC() {
        System.out.println("C start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("C end "+Thread.currentThread().getName());
    }

    public synchronized static void printD() {
        System.out.println("D start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("D end "+Thread.currentThread().getName());
    }

    public static void testWaitNotify() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                runThread1();
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                runThread2();
            }
        });
    }

    private static void runThread1() {
        System.out.println("start "+Thread.currentThread().getName());
        synchronized (object) {
            while (!condition) {
                try {
                    // 阻塞线程, 释放对象锁
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            doSomething();
        }
    }

    private static void doSomething() {
        System.out.println("continue thread1");
    }

    private static void runThread2() {
        System.out.println("start "+Thread.currentThread().getName());
        synchronized (object) {
            try {
                // 阻塞线程, 持有对象锁
                Thread.sleep(1000*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition = true;
            // 唤醒thread1，待退出同步块，thread1获得锁继续执行
            object.notify();
        }
    }
}
