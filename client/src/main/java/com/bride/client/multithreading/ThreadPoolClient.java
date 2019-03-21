package com.bride.client.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ExecutorService线程池
 * <p>Created by shixin on 2018/9/8.
 */
public class ThreadPoolClient {

    public static void main(String[] args) {
        testThreadPoolExecutor();
    }

    public static void testThreadPoolExecutor() {
        // LinkedBlockingDeque先进先出，通过执行execute方法新任务入队
//        LinkedBlockingQueue(AsyncTask128), ArrayBlockingQueue, PriorityBlockingQueue, SynchronousQueue
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(cpuCount+1, cpuCount*2+1,
                1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(6), new ThreadFactory() {
            private final AtomicInteger mAtomicInteger = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "creative-"+mAtomicInteger.getAndIncrement());
            }
        }, new ThreadPoolExecutor.DiscardOldestPolicy()/* 默认ThreadPoolExecutor.AbortPolicy，超出报RejectedExecutionException */);
        // 允许核心线程被销毁
        executor.allowCoreThreadTimeOut(true);

        for(int i=0;i<executor.getCorePoolSize();i++) {
            executor.execute(new MyRunnable());
        }

        for (int i=executor.getQueue().remainingCapacity();i>0;i--) {
            executor.execute(new MyRunnable());
        }

        for(int i=0;i<executor.getMaximumPoolSize();i++) {
            executor.execute(new MyRunnable());
        }

        executor.execute(new MyRunnable());
    }

    private static class MyRunnable implements Runnable {

        private static int count = 1;
        private int i = count++;

        @Override
        public void run() {
            System.out.println("start "+i+") "+Thread.currentThread().getName()+" "+System.currentTimeMillis());
            try {
                Thread.sleep(10*1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testFixedThreadPool() {
        // 1、核心线程数和最大线程数一样, 超时0，LinkedBlockingQueue
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.shutdown();
        try {
            executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void testCachedThreadPool() {
        // 3、核心线程0，最大线程MAX, 超时60秒，SynchronousQueue
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        executorService2.shutdownNow();
    }
}
