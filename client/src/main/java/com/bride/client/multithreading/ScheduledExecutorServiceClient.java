package com.bride.client.multithreading;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Created by shixin on 2019/3/21.
 */
public class ScheduledExecutorServiceClient {

    public static void main(String[] args) {
        testSingleThreadScheduledExecutor();
    }

    public static void testScheduledThreadPool() {
        // 定时执行任务。核心线程数指定，最大线程MAX_VALUE, 超时10毫秒，DelayedWorkQueue
        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(3);

        // delay后执行一次
//        scheduler.schedule(new MyRunnable(), 2000L, TimeUnit.MILLISECONDS);

        // delay后执行第一个任务。一个任务执行完且period到了，执行下一个。
        System.out.println(Thread.currentThread().getName()+" "+System.currentTimeMillis());
        scheduler.scheduleAtFixedRate(new MyRunnable(), 2000L, 3000L, TimeUnit.MILLISECONDS);

        // initialDelay后执行首个任务。再delay后，执行下一个。
//        scheduler.scheduleWithFixedDelay(new MyRunnable(), 2000L, 3000L, TimeUnit.MILLISECONDS);
    }

    public static void testSingleThreadScheduledExecutor() {
        // DelegatedScheduledExecutorService, 核心线程数1, 最大线程MAX_VALUE, 超时10毫秒，DelayedWorkQueue
        ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        singleThreadScheduledExecutor.scheduleWithFixedDelay(new MyRunnable(), 2, 3, TimeUnit.SECONDS);
    }

    private static class MyRunnable implements Runnable {

        private static int count = 1;
        private final int id = count++;

        @Override
        public void run() {
            System.out.println("start "+id+") "+Thread.currentThread().getName()+" "+System.currentTimeMillis());
            try {
                Thread.sleep(10*1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
