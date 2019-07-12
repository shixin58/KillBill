package com.bride.client.datastructure;

import androidx.annotation.NonNull;


import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * <p>Created by shixin on 2019-06-18.
 */
public class References {
    private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<>();

    public static void checkQueue() {
        Reference<? extends VeryBig> inq = rq.poll();
        if (inq != null) {
            System.out.println("In queue: "+inq+"|"+inq.get());
        }
    }

    public static void main(String[] args) {
        int size = 10;
        LinkedList<SoftReference<VeryBig>> sa = new LinkedList<>();
        for (int i=0; i<size; i++) {
            sa.add(new SoftReference<>(new VeryBig("Soft " + i), rq));
            System.out.println("Just created "+sa.getLast());
            checkQueue();
        }
        LinkedList<CustomWeakReference<VeryBig>> wa = new LinkedList<>();
        for (int i=0; i<size; i++) {
            wa.add(new CustomWeakReference<>(new VeryBig("Weak " + i), rq));
            System.out.println("Just created "+wa.getLast());
            checkQueue();
        }
        VeryBig veryBig = wa.getLast().get();
        SoftReference<VeryBig> sr = new SoftReference<>(new VeryBig("Soft"));
        CustomWeakReference<VeryBig> wr = new CustomWeakReference<>(new VeryBig("Weak "+size));
        System.gc();
        LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<>();
        for (int i=0; i<size; i++) {
            pa.add(new PhantomReference<>(new VeryBig("Phantom "+i), rq));
            System.out.println("Just created "+pa.getLast());
            checkQueue();
        }
    }
}

class VeryBig {
    private static final int SIZE = 10000;
    private long[] la = new long[SIZE];
    private String ident;

    public VeryBig(String id) {
        ident = id;
    }

    @NonNull
    @Override
    public String toString() {
        return ident;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalizing "+ident);
    }
}

class CustomWeakReference<T> extends WeakReference<T> {
    private static int counter = 0;
    private final int id = counter++;

    public CustomWeakReference(T referent) {
        super(referent);
    }

    public CustomWeakReference(T referent, ReferenceQueue<? super T> q) {
        super(referent, q);
    }

    @NonNull
    @Override
    public String toString() {
        return "CustomWeakReference - "+id;
    }
}