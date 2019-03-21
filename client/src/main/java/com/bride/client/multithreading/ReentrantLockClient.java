package com.bride.client.multithreading;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、可重用锁ReentrantLock和Condition
 * <p>2、join和interrupt
 * <p>Created by shixin on 2018/9/27.
 */
public class ReentrantLockClient {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void method() {
        lock.lock();
        System.out.println("method "+lock.getHoldCount());
        method2();
        lock.unlock();
    }

    public void method2() {
        lock.lock();
        System.out.println("method2 "+lock.getHoldCount());
        lock.unlock();
    }

    public void waitMethod() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("before wait");
                    condition.await();
                    System.out.println("after wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        });
        thread1.start();
    }

    public void signalMethod() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("before signal");
                condition.signal();
                System.out.println("after signal");
                lock.unlock();
            }
        }).start();
    }

    public void joinMethod() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println("线程开始执行 "+thread.getName()+" "+thread.getId());
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行后续指令 "+Thread.currentThread().getName()+" "+Thread.currentThread().getId());
    }

    public void joinMethod2() {
        List<Thread> list = new Vector<>();
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
            }
        };
        list.add(thread);
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                super.run();
            }
        };
        list.add(thread1);
        // foreach使用迭代器
        for(Thread t:list) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // java.lang.Integer.IntegerCache, 维护一个static Integer数组
        Integer[] integers = {1, 2, Integer.valueOf(3), null};
        for(Integer integer:integers) {
            // 自动拆箱空指针
            int i = integer.intValue();
        }
    }

    public void interruptMethod() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("before sleep");
                    Thread.sleep(10*1000L);
                    System.out.println("after sleep");
                } catch (InterruptedException e) {
                    System.out.println("catch "+Thread.currentThread().isInterrupted());
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

    public static void main(String[] args) {
        final ReentrantLockClient client = new ReentrantLockClient();
//        client.method();

//        client.waitMethod();
//        client.signalMethod();

//        client.joinMethod();

//        client.interruptMethod();

//        client.joinMethod2();
    }
}
