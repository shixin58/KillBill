package com.bride.client.multithreading;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * <p>Created by shixin on 2019-08-06.
 */
public class OptimisticPessimisticConcurrencyControlClient {
    static AtomicInteger atomicInteger = new AtomicInteger(1);
    public static void main(String[] args) {
        // 乐观锁：读数据时直接读；写数据时判断别人有没更新数据，若有则不执行任何操作。
        int oldValue = atomicInteger.get();
        int newValue = oldValue+1;
        // value用volatile修饰，getAndSet、compareAndSet采用Unsafe实现。
        if (!atomicInteger.compareAndSet(oldValue, newValue)) {
            System.out.println("写失败，expect值不等于内存值");
        } else {
            System.out.println("AtomicInteger写成功");
        }

        // CAS仅对单个共享变量有效。AtomicReference保证引用对象的原子性，可将多个共享变量放在1个对象里。
        AtomicReference<String> atomicReference = new AtomicReference<>();
        atomicReference.set("Victor");
        boolean exchanged = atomicReference.compareAndSet("Victor", "Max");
        System.out.println("AtomicReference exchanged "+exchanged);

        // AtomicStampedReference解决ABA问题
        AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("aaa", 1);
        // 读取当前reference和stamp，供提交更新时做判断
        int[] stampHolder = new int[1];
        String oldTxt = atomicStampedReference.get(stampHolder);
        if (!atomicStampedReference.compareAndSet(oldTxt, "bbb", stampHolder[0], stampHolder[0]+1)) {
            System.out.println("写失败，expect引用或expect标志不等于内存值或标志");
        } else {
            System.out.println("AtomicStampedReference修改成功");
        }
    }
}
