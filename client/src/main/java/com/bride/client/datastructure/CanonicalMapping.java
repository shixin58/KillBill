package com.bride.client.datastructure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * <p>Created by shixin on 2019-06-18.
 */
public class CanonicalMapping {
    public static void main(String[] args) {
        int size = 1000;
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
        Set<Map.Entry<Key, Value>> set = map.entrySet();
        for (Map.Entry<Key, Value> entry:set) {
            System.out.println(entry.getKey()+"->"+entry.getValue());
        }
    }
}

class Element {
    private String ident;
    public Element(String id) {
        this.ident = id;
    }

    @NonNull
    @Override
    public String toString() {
        return this.ident;
    }

    @Override
    public int hashCode() {
        return ident.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Element && (ident.equals(((Element)obj).ident));
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalizing "+getClass().getSimpleName()+" "+ident);
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