package com.bride.client.language.coffee;

import androidx.annotation.NonNull;

/**
 * <p>Created by shixin on 2019-05-05.
 */
public class Coffee {
    private static long counter = 0;
    private long id = counter++;

    @NonNull
    @Override
    public String toString() {
        return getClass().getName()+"[counter = "+counter+", id = "+id+"]";
    }
}
