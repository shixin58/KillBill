package com.bride.client.multithreading;

import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * <p>Created by shixin on 2019/3/21.
 */
public class FutureClient {

    public static void main(String[] args) {
        testFuture();
    }

    public static void testFuture() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        final Vector<Future> vector = new Vector<>();
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
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
                    Thread.sleep(6000);
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
                System.out.println("所有工作线程执行完毕，切换主线程");
            }
        });
    }

    public static void testSingleThreadExecutor() {
        // 2、核心线程数和最大线程数均为1, 超时0，LinkedBlockingQueue
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // Future
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5*1000L);
                return "OK";
            }
        });
        Future<String> future1 = executorService.submit(new Runnable() {
            @Override
            public void run() {

            }
        }, "OK");
        Future<?> future2 = executorService.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        });
        executorService.submit(futureTask);
        if(future.isDone() && future1.isDone() && future2.isDone() && futureTask.isDone()) {

        }
    }
}
