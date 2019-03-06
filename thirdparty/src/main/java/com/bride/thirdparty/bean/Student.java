package com.bride.thirdparty.bean;

import javax.inject.Inject;

/**
 * <p>Created by shixin on 2019/3/6.
 */
public class Student {

    private Classroom classroom;

    @Inject
    public Student() {

    }

    public Student(Classroom classroom) {
        this.classroom = classroom;
    }
}
