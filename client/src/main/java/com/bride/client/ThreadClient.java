package com.bride.client;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
//        LinkedBlockingQueue(AsyncTask128), ArrayBlockingQueue, PriorityBlockingQueue, SynchronousQueue
        // 默认ThreadPoolExecutor.AbortPolicy，超出报RejectedExecutionException
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = new ThreadPoolExecutor(cpuCount+1, cpuCount*2+1,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(6), new ThreadFactory() {
            private final AtomicInteger mAtomicInteger = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "creative-"+mAtomicInteger.getAndIncrement());
            }
        }, new ThreadPoolExecutor.DiscardOldestPolicy());
        // 允许核心线程被销毁
        ((ThreadPoolExecutor) executorService).allowCoreThreadTimeOut(true);
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                runThread1();
//            }
//        });
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                runThread2();
//            }
//        });
        /*executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());

        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());

        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());

        executorService.execute(new MyRunnable());*/

        testOtherThreadPool();
    }

    private static void runThread1() {
        System.out.println("start "+Thread.currentThread().getName());
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
        System.out.println("start "+Thread.currentThread().getName());
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

    private static void testOtherThreadPool() {
        // 核心线程数和最大线程数一样，线程不销毁
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.shutdown();
        // 单个工作线程
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        Future<String> future = executorService1.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5*1000L);
                return "OK";
            }
        });
        /*try {
            System.out.println("future "+future.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        // 核心线程0，最大线程MAX, SynchronousQueue
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        executorService2.shutdownNow();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
//        scheduledExecutorService.schedule(new MyRunnable(), 2, TimeUnit.SECONDS);
        System.out.println("start "+Thread.currentThread().getName()+" "+System.currentTimeMillis());
        // 一个任务执行完再执行下一个
        scheduledExecutorService.scheduleAtFixedRate(new MyRunnable(), 2, 3, TimeUnit.SECONDS);
//        scheduledExecutorService.scheduleWithFixedDelay(new MyRunnable(), 2, 3, TimeUnit.SECONDS);
    }
}
