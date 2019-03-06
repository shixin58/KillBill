package com.bride.thirdparty.bean;

import com.bride.thirdparty.protocal.DaggerCarComponent;

import javax.inject.Inject;

/**
 * <p>Created by shixin on 2019/3/6.
 */
public class Car {

    @Inject
    Tyre tyre;

    @Inject
    Chair chair;

    public Car() {
        DaggerCarComponent.builder().build().injectCar(this);
    }
}
