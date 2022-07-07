package com.bride.client.multithreading;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 展示线程池用法
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
        // 参考AsyncTask，用AtomicInteger#getAndIncrement()生成线程名称，并发环境保证唯一
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

        @Override
        public String toString() {
            return getClass().getSimpleName()+" #"+id;
        }
    }

    public static void testCachedThreadPool() {
        // 模拟Executors.newCachedThreadPool()，核心线程0，最大线程Integer.MAX_VALUE, 超时60秒，SynchronousQueue无容量
        ExecutorService executor = new ThreadPoolExecutor(0,
                Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        executor.shutdownNow();
        try {
            executor.awaitTermination(1000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void testFuture() {
        // 核心线程数和最大线程数一样, 超时0，LinkedBlockingQueue容量为Integer.MAX_VALUE
        // 线程数可控。既可最大化服务器使用率，又可预防流量突然增大占用服务器过多资源。
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
            }
        });
    }

    // 串行执行所有任务，如产品经理调研需求->设计师出设计稿->工程师开发->测试人员测试新功能
    public static void testSingleThreadExecutor() {
        // 核心线程数和最大线程数均为1, 超时0，底层数据结构为LinkedBlockingQueue
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ArrayList<Future<?>> team = new ArrayList<>();

        Future<String> futurePm = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5*1000L);
                return "OK";
            }
        });
        team.add(futurePm);

        Future<String> futureUi = executor.submit(new Runnable() {
            @Override
            public void run() {
            }
        }, "OK");
        team.add(futureUi);

        Future<?> futureRd = executor.submit(new Runnable() {
            @Override
            public void run() {
            }
        });
        team.add(futureRd);

        FutureTask<Integer> futureQa = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        });
        executor.submit(futureQa);
        team.add(futureQa);

        // Future#get()阻塞main线程
        for (Future<?> worker : team) {
            try {
                worker.get(10L, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
