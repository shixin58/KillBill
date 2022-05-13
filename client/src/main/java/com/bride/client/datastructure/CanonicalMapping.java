package com.bride.client.datastructure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 练习WeakHashMap用法
 * <p>canonical依据教规的，经典的，标准的
 * <p>Created by shixin on 2019-06-18.
 */
public class CanonicalMapping {
    public static void main(String[] args) {
        final int size = 1000;
        Key[] keys = new Key[size];
        WeakHashMap<Key, Value> map = new WeakHashMap<>();
        for (int i=0; i<size; i++) {
            Key k = new Key(Integer.toString(i));
            Value v = new Value(Integer.toString(i));
            if (i % 3 == 0)
                keys[i] = k;
            map.put(k, v);
        }
        System.gc();
        // 打印map
        Set<Map.Entry<Key, Value>> set = map.entrySet();
        for (Map.Entry<Key, Value> entry:set) {
            System.out.println(entry.getKey()+"->"+entry.getValue());
        }
    }
}

class Element {
    private final String identity;
    public Element(String id) {
        this.identity = id;
    }

    @NonNull
    @Override
    public String toString() {
        return this.identity;
    }

    @Override
    public int hashCode() {
        return identity.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Element && (identity.equals(((Element)obj).identity));
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalizing "+getClass().getSimpleName()+" "+ identity);
    }
}

class Key extends Element {
    public Key(String id) {
        super(id);
    }
}

class Value extends Element {
    public Value(String id) {
        super(id);
    }
}