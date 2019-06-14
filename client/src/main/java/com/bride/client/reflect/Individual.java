package com.bride.client.reflect;

/**
 * <p>Created by shixin on 2019-06-12.
 */
public interface Individual {

    void born();

    class Factory {
        public static void print() {
            System.out.println("Welcome");
        }
    }
}
