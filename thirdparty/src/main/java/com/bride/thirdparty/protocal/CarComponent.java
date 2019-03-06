package com.bride.thirdparty.protocal;

import com.bride.thirdparty.bean.Car;

import dagger.Component;

/**
 * <p>Created by shixin on 2019/3/6.
 */
@Component
public interface CarComponent {
    void injectCar(Car car);
}
