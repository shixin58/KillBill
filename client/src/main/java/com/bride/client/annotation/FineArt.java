package com.bride.client.annotation;

/**
 * <p>Created by shixin on 2018/9/1.
 */
@Curriculum(value = "Math", age = 300)
public class FineArt {

    @Stone
    public void doSculpture() {
    }

    @Deprecated
    @Stone
    public void doArchitecture(@Superman(height = 2.2f) String type) {
    }
}
