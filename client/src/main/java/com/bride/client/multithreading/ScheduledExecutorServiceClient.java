package com.bride.client.multithreading;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>Created by shixin on 2019/3/21.
 */
public class ScheduledExecutorServiceClient {

    public static void main(String[] args) {
        testSingleThreadScheduledExecutor();
    }

    public static void testScheduledThreadPool() {
        // 4、定时执行任务。核心线程数指定，最大线程MAX_VALUE, 超时10毫秒，DelayedWorkQueue
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

        // delay后执行一次
        scheduledExecutorService.schedule(new MyRunnable(), 2, TimeUnit.SECONDS);

        // delay后执行第一个任务，一个任务执行完且period到了，再执行下一个
        System.out.println(Thread.currentThread().getName()+" "+System.currentTimeMillis());
        scheduledExecutorService.scheduleAtFixedRate(new MyRunnable(), 2, 3, TimeUnit.SECONDS);

        // delay后执行下一个任务，执行完，再delay，再执行下一个
        scheduledExecutorService.scheduleWithFixedDelay(new MyRunnable(), 2, 3, TimeUnit.SECONDS);
    }

    public static void testSingleThreadScheduledExecutor() {
        // 5、核心线程数1，最大线程MAX_VALUE, 超时10毫秒，DelayedWorkQueue
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new MyRunnable(), 2, 3, TimeUnit.SECONDS);
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
}
