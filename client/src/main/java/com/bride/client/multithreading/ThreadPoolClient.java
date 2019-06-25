package com.bride.client.multithreading;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ExecutorService线程池
 * LinkedBlockingQueue(AsyncTask128), SynchronousQueue, ArrayBlockingQueue, PriorityBlockingQueue
 * <p>Created by shixin on 2018/9/8.
 */
public class ThreadPoolClient {

    public static void main(String[] args) {
        testThreadPoolExecutor();
    }

    public static void testThreadPoolExecutor() {
        // Honor V10 is 8 cores, MacBook Pro is 4 cores.
        int cpuCount = Runtime.getRuntime().availableProcessors();
        System.out.printf("availableProcessors: %d\n", cpuCount);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 9,
                1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(16), sThreadFactory,
                new CustomPolicy());
        executor.allowCoreThreadTimeOut(true);

        for (int i=0; i < 4; i++) {
            executor.execute(new RoutineTask());
        }

        for (int i=0; i < 16; i++) {
            executor.execute(new RoutineTask());
        }

        for (int i=0; i < 5; i++) {
            executor.execute(new RoutineTask());
        }

        executor.execute(new RoutineTask());
        executor.shutdown();
    }

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(0);
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "CreativeThread #"+mCount.getAndIncrement());
            System.out.println("newThread "+t.getName());
            return t;
        }
    };

    private static class CustomPolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                Runnable polled = e.getQueue().poll();
                System.out.println("poll "+polled+", execute "+r);
                e.execute(r);
            }
        }
    }

    private static class RoutineTask implements Runnable {

        private static int count = 0;
        private final int id = count++;

        @Override
        public void run() {
            System.out.println("run "+this+" in "+Thread.currentThread().getName()+" at "+System.currentTimeMillis());
            try {
                Thread.sleep(10*1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @NonNull
        @Override
        public String toString() {
            return getClass().getSimpleName()+" #"+id;
        }
    }

    public static void testFixedThreadPool() {
        // 模拟Executors.newFixedThreadPool(3)，核心线程数和最大线程数一样, 超时0，LinkedBlockingQueue容量为Integer.MAX_VALUE
        ExecutorService executorService = new ThreadPoolExecutor(3,
                3, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        executorService.shutdown();
        try {
            executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void testCachedThreadPool() {
        // 模拟Executors.newCachedThreadPool()，核心线程0，最大线程Integer.MAX_VALUE, 超时60秒，SynchronousQueue无容量
        ExecutorService executorService = new ThreadPoolExecutor(0,
                Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        executorService.shutdownNow();
    }
}
