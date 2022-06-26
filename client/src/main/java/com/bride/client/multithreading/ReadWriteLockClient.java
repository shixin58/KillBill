package com.bride.client.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadLock为共享锁，WriteLock为排它锁。
 * <p>WriteLock支持多个Condition，ReadLock不支持Condition。
 * <p>Created by shixin on 2019-04-26.
 */
public class ReadWriteLockClient {

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {

        ReadWriteLockClient client = new ReadWriteLockClient();
        // 1个线程获得读锁，其它线程不能获得写锁；
        // 1个线程获得写锁，其它线程不能获得读锁；
        // 写锁被释放时，多个线程可同时获得读锁；
        read(client);
        read(client);
//        write(client);

//        downgrade(client);

//        Condition condition = client.readWriteLock.writeLock().newCondition();
//        await(client.readWriteLock.writeLock(), condition);
//        signal(client.readWriteLock.writeLock(), condition);
    }

    public static void read(ReadWriteLockClient client) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                ReentrantReadWriteLock.ReadLock readLock = client.readWriteLock.readLock();
                readLock.lock();
                System.out.println(Thread.currentThread().getName()+"获得读锁 reading...");
                try {
                    Thread.sleep(3000L);// 模拟执行读任务3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" read sb "+client.sb);
                readLock.unlock();
            }
        }.start();
    }

    public static void write(ReadWriteLockClient client) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                ReentrantReadWriteLock.WriteLock writeLock = client.readWriteLock.writeLock();
                writeLock.lock();
                System.out.println(Thread.currentThread().getName()+"获得写锁 writing...");
                try {
                    Thread.sleep(3000L);// 模拟执行写任务3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.sb.append('A');
                System.out.println(Thread.currentThread().getName()+" write sb "+client.sb);
                writeLock.unlock();
            }
        }.start();
    }

    // 同个线程里写锁降级为读锁
    public static void downgrade(ReadWriteLockClient client) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                ReentrantReadWriteLock.ReadLock readLock = client.readWriteLock.readLock();
                ReentrantReadWriteLock.WriteLock writeLock = client.readWriteLock.writeLock();

                writeLock.lock();
                System.out.println("获得写锁 writing...");
                try {
                    Thread.sleep(3000L);// 模拟执行写任务3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.sb.append('A');
                System.out.println("write sb "+client.sb);

                readLock.lock();
                writeLock.unlock();

                System.out.println("写锁降级为读锁 reading...");
                try {
                    Thread.sleep(3000L);// 模拟执行读任务3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("read sb "+client.sb);
                readLock.unlock();
            }
        }.start();
    }

    public static void await(ReentrantReadWriteLock.WriteLock writeLock, Condition condition) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                writeLock.lock();
                try {
                    System.out.println("awaiting...");
                    condition.await();
                    System.out.println("await is over");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writeLock.unlock();
            }
        }.start();
    }

    public static void signal(ReentrantReadWriteLock.WriteLock writeLock, Condition condition) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                writeLock.lock();
                try {
                    Thread.sleep(5000L);// 模拟执行任务5秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("signal");
                condition.signal();
                writeLock.unlock();
            }
        }.start();
    }
}
