package com.bride.client.datastructure;

import androidx.annotation.NonNull;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * 练习软引用、弱引用、虚引用
 * <p>Created by shixin on 2019-06-18.
 */
public class References {
    private static final ReferenceQueue<VeryBig> rq = new ReferenceQueue<>();

    public static void checkQueue() {
        Reference<? extends VeryBig> inq = rq.poll();
        if (inq != null) {// 若非空，其引用对象已被回收
            // Reference#get()
            System.out.println("In queue: "+inq+"|"+inq.get());
        }
    }

    public static void main(String[] args) {
        final int size = 10;

        // SoftReference Array
        LinkedList<SoftReference<VeryBig>> sa = new LinkedList<>();
        for (int i=0; i<size; i++) {
            // 创建弱引用，将其关联到引用队列，将其添加到双链表队尾
            sa.add(new SoftReference<>(new VeryBig("Soft " + i), rq));
            // SoftReference#get()
            // 取出双链表队尾弱引用，检索其引用对象。
            System.out.println("Just created SoftReference "+sa.getLast().get());
            checkQueue();
        }

        // WeakReference Array
        LinkedList<CustomWeakReference<VeryBig>> wa = new LinkedList<>();
        for (int i=0; i<size; i++) {
            // 将弱引用注册到引用队列rq
            VeryBig referent = new VeryBig("Weak " + i);
            wa.add(new CustomWeakReference<>(referent, rq));
            System.out.println("Just created "+wa.getLast());
            checkQueue();
        }
        VeryBig veryBig = wa.getLast().get();
        SoftReference<VeryBig> sr = new SoftReference<>(new VeryBig("Soft"));
        CustomWeakReference<VeryBig> wr = new CustomWeakReference<>(new VeryBig("Weak "+size));
        System.gc();

        // PhantomReference Array
        LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<>();
        for (int i=0; i<size; i++) {
            pa.add(new PhantomReference<>(new VeryBig("Phantom "+i), rq));
            System.out.println("Just created PhantomReference "+pa.getLast().get());
            checkQueue();
        }
    }
}

class VeryBig {
    private static final int SIZE = 10000;
    // long array
    private final long[] la = new long[SIZE];
    private final String identity;

    public VeryBig(String id) {
        identity = id;
    }

    @NonNull
    @Override
    public String toString() {
        return identity;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalizing "+ identity);
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