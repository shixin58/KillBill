package com.bride.client.multithreading;

import java.util.List;
import java.util.Vector;

/**
 * <p>Created by shixin on 2019/3/21.
 */
public class ThreadClient {

    public static void main(String[] args) {
//        joinMethod();
//        joinMethod2();

        interruptMethod();
    }

    public static void joinMethod() {
        Thread thread = new MyThread();
        thread.start();
        System.out.println("线程开始执行 "+thread.getName()+" "+thread.getId());
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行后续指令 "+Thread.currentThread().getName()+" "+Thread.currentThread().getId());
    }

    public static void joinMethod2() {
        List<Thread> list = new Vector<>();
        Thread thread = new MyThread();
        thread.start();
        list.add(thread);
        Thread thread1 = new MyThread();
        thread1.start();
        list.add(thread1);
        for(Thread t:list) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void interruptMethod() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("before sleep");
                    Thread.sleep(10*1000L);
                    System.out.println("after sleep");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 跳出sleep/join/wait，抛InterruptedException
        thread.interrupt();
        // catch之后
        System.out.println("interrupt工作线程 "+thread.isInterrupted());
    }
}
