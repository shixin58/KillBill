package com.bride.client.datastructure;

import androidx.annotation.NonNull;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 模拟CopyOnWriteArrayList、DelayQueue原理
 * <p>Created by shixin on 2019-04-23.
 */
public class ConcurrentClient {

    public static void main(String[] args) {
//        reviewCopyOnWriteArrayList();
        reviewDelayQueue();
    }

    // 案例1 - CopyOnWriteArrayList
    // 原理：add()/remove()用synchronized上锁，get()直接读取底层数组。
    // 优缺点：用于读多写少的场景，如白名单、黑名单。但占用内存多、不能保证数据实时一致性。
    // 实际应用：EventBus#subscriptionsByEventType。
    public static void reviewCopyOnWriteArrayList() {
        CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
        arrayList.add("Apple");
        arrayList.set(0, "Banana");
        CopyOnWriteArrayList<String> sublist = new CopyOnWriteArrayList<>();
        sublist.add("Candy");
        // Arrays#copyOf(Object[], int)
        sublist.toArray();
        // Iterable#iterator()
        sublist.iterator();
        arrayList.addAll(sublist);
        arrayList.indexOf("Banana", 1);
        System.out.println(arrayList.get(0));

        CopyOnWriteArraySet<String> arraySet = new CopyOnWriteArraySet<>();
        arraySet.add("Day");
        arraySet.contains("Day");
    }

    // 案例2 - DelayQueue：无界；item到期才能取走；均未到期poll返回null；到期时间最长为队头
    public static void reviewDelayQueue() {
        Random random = new Random(31);
        ExecutorService exec = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        for (int i=0; i<20; i++)
            queue.put(new DelayedTask(random.nextInt(5000)));
        queue.add(new EndSentinel(5000L, exec));
        exec.execute(new DelayedTaskConsumer(queue));
    }

    static class DelayedTask implements Delayed, Runnable {
        private static int counter = 0;
        private final int id = counter++;

        final long delta;
        final long trigger;

        DelayedTask(long delayInMilliseconds) {
            this.delta = delayInMilliseconds;
            this.trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            DelayedTask delayedTask = (DelayedTask) o;
            if (trigger < delayedTask.trigger)
                return -1;
            if (trigger > delayedTask.trigger)
                return 1;
            return 0;
        }

        @Override
        public void run() {
            System.out.println(this + " is running...");
        }

        @NonNull
        @Override
        public String toString() {
            return getClass().getSimpleName()+"["+id+", "+delta+"]";
        }
    }

    // sentinel哨兵
    static class EndSentinel extends DelayedTask {
        private final ExecutorService exec;
        EndSentinel(long delayInMilliseconds, ExecutorService exec) {
            super(delayInMilliseconds);
            this.exec = exec;
        }

        @Override
        public void run() {
            super.run();
            exec.shutdownNow();
        }
    }

    static class DelayedTaskConsumer implements Runnable {
        private final DelayQueue<DelayedTask> delayQueue;
        DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
            delayQueue = q;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    delayQueue.take().run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
