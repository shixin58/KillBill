package com.bride.client.multithreading;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 每个对象有且仅有1个同步锁，不同线程对同步锁的访问是互斥的。
 * <p>1、synchronized同步锁为可重入锁(递归锁)，避免死锁，提升封装性。
 * <p>2、wait/notify等待/唤醒，Object锁。
 * <p>3、进入同步代码块获得锁，退出同步代码块或抛异常后释放锁。
 * <p>4、synchronized不可中断，即等待锁时不可中断，Thread.interrupt()无效。ReentrantLock#lockInterruptibly()可中断
 * <p>5、synchronized可见性(Java内存模型)。一个线程退出同步代码块，保证其执行结果在另一个线程进入同步代码块时可见。
 * <p>Created by shixin on 2019/3/20.
 */
public class SynchronizedClient {

    private static final Object object = new Object();
    private static boolean condition = false;

    public static void main(String[] args) {
        testSynchronized();
//        testWaitNotify();
    }

    // A、B为对象锁，锁住SynchronizedClient；C、D为类锁，锁住SynchronizedClient.class
    public static void testSynchronized() {
        final SynchronizedClient synchronizedClient = new SynchronizedClient();
        Executor executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                synchronizedClient.printA();
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                synchronizedClient.printB();
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SynchronizedClient.printC();
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SynchronizedClient.printD();
            }
        });
    }

    public synchronized void printA() {
        Thread t = Thread.currentThread();
        System.out.println("A start in "+t.getName()+" "+t.getId()+" at "+System.currentTimeMillis());
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("A end in "+t.getName()+" "+t.getId()+" at "+System.currentTimeMillis());
    }

    public synchronized void printB() {
        Thread t = Thread.currentThread();
        System.out.println("B start in "+t.getName()+" "+t.getId()+" at "+System.currentTimeMillis());
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("B end in "+t.getName()+" "+t.getId()+" at "+System.currentTimeMillis());
    }

    public synchronized static void printC() {
        Thread t = Thread.currentThread();
        System.out.println("C start in "+t.getName()+" "+t.getId()+" at "+System.currentTimeMillis());
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("C end in "+t.getName()+" "+t.getId()+" at "+System.currentTimeMillis());
    }

    public synchronized static void printD() {
        Thread t = Thread.currentThread();
        System.out.println("D start in "+t.getName()+" "+t.getId()+" at "+System.currentTimeMillis());
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("D end in "+t.getName()+" "+t.getId()+" at "+System.currentTimeMillis());
    }

    public static void testWaitNotify() {
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

    private static void runThread1() {
        System.out.println("start T1 "+Thread.currentThread().getName());
        synchronized (object) {
            while (!condition) {
                try {
                    // 阻塞线程, 释放对象锁
                    System.out.println("T1 wait");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            doSomething();
        }
    }

    private static void doSomething() {
        System.out.println("continue T1");
    }

    private static void runThread2() {
        System.out.println("start T2 "+Thread.currentThread().getName());
        synchronized (object) {
            try {
                // 持有对象锁，阻塞当前线程10秒
                Thread.sleep(1000L * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition = true;
            // 唤醒T1，待T2退出同步块，T1获得锁继续执行
            System.out.println("T2 notify");
            object.notify();
        }
    }
}
