package com.bride.client.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadLock为共享锁，WriteLock为排它锁。WriteLock支持多个Condition
 * <p>Created by shixin on 2019-04-26.
 */
public class ReadWriteLockClient {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {

        ReadWriteLockClient client = new ReadWriteLockClient();

//        write(client);

//        downgrade(client);

//        read(client);

        Condition condition = client.readWriteLock.writeLock().newCondition();

        await(client.readWriteLock.writeLock(), condition);

        signal(client.readWriteLock.writeLock(), condition);
    }

    public static void read(ReadWriteLockClient client) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                client.readWriteLock.readLock().lock();
                System.out.println("reading...");
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("read sb "+client.sb);
                client.readWriteLock.readLock().unlock();
            }
        }.start();
    }

    public static void write(ReadWriteLockClient client) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                client.readWriteLock.writeLock().lock();
                System.out.println("writing...");
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.sb.append('A');
                System.out.println("write sb "+client.sb);
                client.readWriteLock.writeLock().unlock();
            }
        }.start();
    }

    public static void downgrade(ReadWriteLockClient client) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                client.readWriteLock.writeLock().lock();
                System.out.println("writing...");
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.sb.append('A');
                System.out.println("downgrade write sb "+client.sb);
                client.readWriteLock.readLock().lock();
                client.readWriteLock.writeLock().unlock();
                System.out.println("reading...");
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("downgrade read sb "+client.sb);
                client.readWriteLock.readLock().unlock();
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
                    Thread.sleep(5000L);
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
