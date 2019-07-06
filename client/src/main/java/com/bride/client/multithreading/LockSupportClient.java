package com.bride.client.multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport阻塞线程前不需要获取同步锁，不可重入，中断阻塞不抛异常。
 * park/unpark类似于suspend/resume, 但不会遇到死锁问题。
 * <p>Created by shixin on 2019-04-26.
 */
public class LockSupportClient {

    public static void main(String[] args) {
//        func1();
        func2();
    }

    private static void func1() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("LockSupport#unpark");
                LockSupport.unpark(this);
                System.out.println("LockSupport#park");
                LockSupport.park();

                // 阻塞10秒，不会抛异常
                System.out.println("LockSupport#parkNanos");
                LockSupport.parkNanos(10_000_000_000L);
                System.out.println("It's over");
            }
        };
        thread.start();
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    private static void func2() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("blocking...");
                LockSupport.park();
                System.out.println("It's over");
            }
        };
        thread.start();
        try {
            TimeUnit.NANOSECONDS.sleep(2_000_000_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("wake up");
        LockSupport.unpark(thread);
    }
}
