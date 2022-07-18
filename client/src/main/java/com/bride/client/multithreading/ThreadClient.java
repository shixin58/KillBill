package com.bride.client.multithreading;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * join和interrupt
 * <p>Created by shixin on 2019/3/21.
 */
public class ThreadClient {

    public static void main(String[] args) {
        joinMethod();
//        joinMethod2();

//        interruptMethod();
//        daemonThread();
    }

    // 案例1 - Thread#Join()
    public static void joinMethod() {
        Thread thread = new MyThread();
        // start()方法标记synchronized、仅能执行1次，started阈标记，否则IllegalThreadStateException
        // isAlive()/getState()标记线程状态
        // Thread-0 13 NEW
        System.out.println("线程start前 "+thread.getName()+" "+thread.getId()+" "+thread.getState());
        thread.start();
        // Thread-0 13 TIMED_WAITING/RUNNABLE
        System.out.println("线程开始执行 "+thread.getName()+" "+thread.getId()+" "+thread.getState());
        try {
            thread.join();
            // started = true, false TERMINATED
            System.out.println("线程执行完 "+thread.isAlive()+" "+thread.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread curT = Thread.currentThread();
        // main 1 RUNNABLE
        System.out.println("执行后续指令 "+curT.getName()+" "+curT.getId()+" "+curT.getState());
    }

    // 案例2 - Thread#Join()
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
                Thread.sleep(3 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 案例3 - Thread#interrupt()
    public static void interruptMethod() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("before sleep");
                    Thread.sleep(10 * 1000L);
                    System.out.println("after sleep");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-sleep");
        thread.start();

        try {
            Thread.sleep(2 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 跳出sleep/join/wait，抛InterruptedException
        System.out.println("interrupt "+thread.getName()+" "+thread.isInterrupted()+" "+thread.isAlive());
        thread.interrupt();

        Thread thread1 = new Thread(new Runnable() {
            int count = Integer.MAX_VALUE;
            int i = 0;

            void f() {
                System.out.println("start f()");
                final long start = System.nanoTime();
                while (count-- > 0 && !Thread.interrupted()) {
                    i = count % 100;
                }
                System.out.println("interval "+(System.nanoTime() - start));
            }

            @Override
            public void run() {
                f();
                System.out.println(Thread.currentThread().getName()+" quit");
            }
        }, "Thread-yield");
        thread1.start();

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("interrupt "+thread1.getName()+" "+thread1.isInterrupted()+" "+thread1.isAlive());
        thread1.interrupt();
    }

    // 案例4 - Thread#setDaemon()
    public static void daemonThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread curThread = Thread.currentThread();
                System.out.println(curThread.getName()+": "+curThread.getPriority()
                        +" - "+curThread.isDaemon());
//                curThread.setPriority(Thread.MAX_PRIORITY);
                try {
                    TimeUnit.MILLISECONDS.sleep(1000L);
                    System.err.println(curThread.getName()+" wake up.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setDaemon(true);
        thread.start();
        System.out.println(Thread.currentThread().getName()+" is over.");
    }
}
