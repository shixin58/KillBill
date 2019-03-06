package com.bride.thirdparty.bean;

import com.bride.thirdparty.protocal.DaggerCollegeComponent;

import javax.inject.Inject;

/**
 * <p>Created by shixin on 2019/3/6.
 */
public class College {
    @Inject
    Student student;
    public College() {
        DaggerCollegeComponent.builder().collegeModule(new CollegeModule()).build().injectCollege(this);
    }
}
