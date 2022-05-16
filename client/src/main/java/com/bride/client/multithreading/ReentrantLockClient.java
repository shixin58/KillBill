package com.bride.client.multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、可重入互斥锁 ReentrantLock和Condition。默认非公平锁(not FIFO)。
 * 2、Condition#await()/signal()类似于Object#wait()/notify()。
 * <p>Created by shixin on 2018/9/27.
 */
public class ReentrantLockClient {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        final ReentrantLockClient client = new ReentrantLockClient();
//        client.method();

//        client.awaitMethod();
//        client.signalMethod();
//        client.tryLock();
        client.lockInterruptibly();
    }

    public void method() {
        lock.lock();
        try {
            System.out.println("method "+lock.getHoldCount());
            method2();
        } finally {
            lock.unlock();
        }
    }

    public void method2() {
        lock.lock();
        try {
            System.out.println("method2 "+lock.getHoldCount());
        } finally {
            lock.unlock();
        }
    }

    public void awaitMethod() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("before wait");
                    condition.await();
                    System.out.println("after wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        thread.start();
    }

    public void signalMethod() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("before signal");
                    condition.signal();
                    System.out.println("after signal");
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }

    public void tryLock() {
        lock.tryLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("before tryLock");
                    boolean acquired = lock.tryLock(2L, TimeUnit.SECONDS);
                    System.out.println("after tryLock "+acquired);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void lockInterruptibly() {
        lock.lock();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                lock.lock();
                try {
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
