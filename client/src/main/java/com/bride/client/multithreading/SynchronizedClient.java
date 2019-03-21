package com.bride.client.multithreading;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <p>Created by shixin on 2019/3/20.
 */
public class SynchronizedClient {
    public static void main(String[] args) {
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
                SynchronizedClient.printC();
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
                SynchronizedClient.printD();
            }
        });
    }

    public synchronized void printA() {
        System.out.println("A start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("A end "+Thread.currentThread().getName());
    }

    public synchronized void printB() {
        System.out.println("B start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("B end "+Thread.currentThread().getName());
    }

    public synchronized static void printC() {
        System.out.println("C start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("C end "+Thread.currentThread().getName());
    }

    public synchronized static void printD() {
        System.out.println("D start "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("D end "+Thread.currentThread().getName());
    }
}
