package com.bride.client.datastructure;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Created by shixin on 2019-04-23.
 */
public class ConcurrentClient {

    public static void main(String[] args) {
        reviewCopyOnWriteArrayList();
        reviewArrayList();
    }

    public static void reviewCopyOnWriteArrayList() {
        CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
        arrayList.add("Apple");
        arrayList.set(0, "Banana");
        CopyOnWriteArrayList<String> sublist = new CopyOnWriteArrayList<>();
        sublist.add("Candy");
        sublist.toArray();
        sublist.iterator();
        arrayList.addAll(sublist);
        arrayList.indexOf("Banana", 1);
        System.out.println(arrayList.get(0));

        CopyOnWriteArraySet<String> arraySet = new CopyOnWriteArraySet<>();
        arraySet.add("Day");
        arraySet.contains("Day");
    }

    public static void reviewArrayList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Joyful");
        Logger.getLogger("Victor").log(Level.WARNING, "Catch a cold!");
        arrayList.remove(0);
    }

    public static void reviewThreadLocal() {
        // ThreadLocal#get() -> Thread.currentThread() -> Thread#threadLocals(ThreadLocalMap)
    }
}
