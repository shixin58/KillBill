package com.max.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 1、synchronized同步锁，可重入锁，保证原子性
 * <p>2、wait/notify等待唤醒
 * <p>3、ExecutorService线程池
 * <p>Created by shixin on 2018/9/8.
 */
public class ThreadClient {

    private static final Object object = new Object();
    private static boolean condition = false;

    public static void main(String[] args) {
        // LinkedBlockingDeque先进先出，通过执行execute方法新任务入队
        ExecutorService executorService = new ThreadPoolExecutor(3, 5,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128));
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
        synchronized (object) {
            while (!condition) {
                try {
                    // 释放对象锁，阻塞线程
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
        try {
            // 持有对象锁，阻塞线程
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (object) {
            condition = true;
            // 唤醒thread1，待退出同步块，thread1获得锁继续执行
            object.notify();
        }
    }
}
