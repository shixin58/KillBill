package com.bride.client.multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、可重入互斥锁 ReentrantLock和Condition，默认非公平锁(not FIFO/NonfairSync)；
 * <p>ReentrantLock#lock()/unlock()搭配Condition#await()/signal()，类似于synchronized块搭配Object#wait()/notify()。
 * <p>2、LinkedBlockingQueue持有两个ReentrantLock，takeLock&notEmpty和putLock&notFull；
 * <p>take/poll但链表为空，则调用notEmpty.await()阻塞；put/offer后，调用notEmpty.signal()唤醒；
 * <p>put/offer但链表已满，则调用notFull.await()阻塞；take/poll后，调用notFull.signal()唤醒；
 * <p>Created by shixin on 2018/9/27.
 */
public class ReentrantLockClient {
    private final ReentrantLock lock = new ReentrantLock(false);
    private final Condition condition = lock.newCondition();

    // ReentrantLock实际应用：CopyOnWriteArrayList#add()/remove()
    public static void main(String[] args) {
        final ReentrantLockClient client = new ReentrantLockClient();
//        client.method();

        client.awaitMethod();
        client.signalMethod();

//        client.tryLock();

//        client.lockInterruptibly();
    }

    // 案例1：测试getHoldCount()
    public void method() {
        lock.lock();
        try {
            System.out.println("method "+lock.getHoldCount());
            method2();
        } finally {// 保证抛异常也会释放锁
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

    // 案例2：测试await()/signal()
    public void awaitMethod() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("awaitMethod获得同步锁");
                try {
                    Thread.sleep(3000L);// 模拟执行任务3秒
                    System.out.println("awaitMethod call await() "+lock.getHoldCount());
                    condition.await();// 释放锁
                    Thread.sleep(3000L);// 模拟执行任务3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("awaitMethod call unlock()");
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
                System.out.println("signalMethod获得同步锁");
                try {
                    Thread.sleep(3000L);// 模拟执行任务3秒
                    System.out.println("signalMethod call signal() "+lock.getHoldCount());
                    condition.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("signalMethod call unlock()");
                    lock.unlock();
                }
            }
        }).start();
    }

    // 案例3：测试tryLock()非阻塞性质
    public void tryLock() {
        boolean acquired = lock.tryLock();// 非阻塞，拿不到锁也立即返回
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

    // 案例4：测试lockInterruptibly()可中断性质
    public void lockInterruptibly() {
        lock.lock();
        System.out.println(Thread.currentThread().getName()+" 获得锁");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // lock()不可中断，lockInterruptibly()可中断
        thread.interrupt();
    }
}
