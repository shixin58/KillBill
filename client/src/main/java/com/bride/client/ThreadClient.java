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
        testThreadPoolExecutor();
        testOtherThreadPool();
        testWaitNotify();
    }

    private static void testWaitNotify() {
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

    private static void testThreadPoolExecutor() {
        // LinkedBlockingDeque先进先出，通过执行execute方法新任务入队
//        LinkedBlockingQueue(AsyncTask128), ArrayBlockingQueue, PriorityBlockingQueue, SynchronousQueue
        // 默认ThreadPoolExecutor.AbortPolicy，超出报RejectedExecutionException
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(cpuCount+1, cpuCount*2+1,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(6), new ThreadFactory() {
            private final AtomicInteger mAtomicInteger = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "creative-"+mAtomicInteger.getAndIncrement());
            }
        }, new ThreadPoolExecutor.DiscardOldestPolicy());
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
        // 1、核心线程数和最大线程数一样，线程不销毁
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.shutdown();

        // 2、单个工作线程
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        Future<String> future = executorService1.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5*1000L);
                return "OK";
            }
        });
        // 3、核心线程0，最大线程MAX, SynchronousQueue
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        executorService2.shutdownNow();

        // 4、定时执行任务
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        // delay后执行一次
//        scheduledExecutorService.schedule(new MyRunnable(), 2, TimeUnit.SECONDS);
        // delay后执行第一个任务，一个任务执行完且period到了，再执行下一个
        System.out.println(Thread.currentThread().getName()+" "+System.currentTimeMillis());
//        scheduledExecutorService.scheduleAtFixedRate(new MyRunnable(), 2, 3, TimeUnit.SECONDS);
        // delay后执行下一个任务，执行完，再delay，再执行下一个
//        scheduledExecutorService.scheduleWithFixedDelay(new MyRunnable(), 2, 3, TimeUnit.SECONDS);

        // 5、单个工作线程
        ScheduledExecutorService scheduledExecutorService1 = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService1.scheduleWithFixedDelay(new MyRunnable(), 2, 3, TimeUnit.SECONDS);
    }
}
