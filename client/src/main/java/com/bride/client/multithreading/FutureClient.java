package com.bride.client.multithreading;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 演练Future用法
 * <p>Created by shixin on 2019/3/21.
 */
public class FutureClient {

    public static void main(String[] args) {
        testFuture();

        testSingleThreadExecutor();
    }

    public static void testFuture() {
        ExecutorService executorService = Executors.newFixedThreadPool(3, new ThreadFactory() {
            int count = 0;
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Victor-"+(count++));
                thread.setPriority(Thread.MAX_PRIORITY);
                thread.setDaemon(true);
                return thread;
            }
        });
        final Vector<Future> vector = new Vector<>();

        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        vector.add(future);

        Future<?> future2 = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        vector.add(future2);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for(Future f : vector) {
                    try {
                        f.get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                executorService.shutdown();
                System.out.println("所有工作线程执行完毕，切换主线程");
            }
        });
    }

    public static void testSingleThreadExecutor() {
        // 2、核心线程数和最大线程数均为1, 超时0，LinkedBlockingQueue
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ArrayList<Future<?>> list = new ArrayList<>();

        // Future
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5*1000L);
                return "OK";
            }
        });
        list.add(future);

        Future<String> future1 = executorService.submit(new Runnable() {
            @Override
            public void run() {
            }
        }, "OK");
        list.add(future1);

        Future<?> future2 = executorService.submit(new Runnable() {
            @Override
            public void run() {
            }
        });
        list.add(future2);

        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        });
        executorService.submit(futureTask);
        list.add(futureTask);

        for (Future<?> f : list) {
            try {
                f.get(10L, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
