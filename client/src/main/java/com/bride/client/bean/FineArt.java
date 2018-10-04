package com.bride.client.bean;

import com.bride.client.annotation.Curriculum;
import com.bride.client.annotation.Stone;
import com.bride.client.annotation.Superman;

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
