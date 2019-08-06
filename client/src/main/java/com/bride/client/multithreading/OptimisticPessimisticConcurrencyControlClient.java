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
        int oldValue = atomicInteger.get();
        int newValue = oldValue+1;
        if (!atomicInteger.compareAndSet(oldValue, newValue)) {
            System.out.println("old value changed from "+oldValue);
        }

        AtomicReference<String> atomicReference = new AtomicReference<>();
        atomicReference.set("Victor");
        boolean exchanged = atomicReference.compareAndSet("Victor", "Max");
        System.out.println("exchanged "+exchanged);
        exchanged = atomicReference.compareAndSet("Victor", "Max");
        System.out.println("exchanged "+exchanged);

        AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("aaa", 1);
//        String oldTxt = atomicStampedReference.getReference();
//        int oldStamp = atomicStampedReference.getStamp();
        int[] stampHolder = new int[1];
        String oldTxt = atomicStampedReference.get(stampHolder);
        if (!atomicStampedReference.compareAndSet(oldTxt, "bbb", stampHolder[0], stampHolder[0]+1)) {
            System.out.println("old value changed from "+oldTxt+"; old stamp changed from "+stampHolder[0]);
        }
    }
}
