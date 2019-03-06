package com.bride.thirdparty.protocal;

import com.bride.thirdparty.bean.College;
import com.bride.thirdparty.bean.CollegeModule;

import dagger.Component;

/**
 * <p>Created by shixin on 2019/3/6.
 */
@Component(modules = CollegeModule.class)
public interface CollegeComponent {
    void injectCollege(College college);
}
