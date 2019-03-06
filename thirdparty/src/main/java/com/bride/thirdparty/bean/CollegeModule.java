package com.bride.thirdparty.bean;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Created by shixin on 2019/3/6.
 */
@Module
public class CollegeModule {
    @Provides
    Classroom provideClassroom() {
        return new Classroom();
    }
    @Provides
    Student provideStudent(Classroom classroom) {
        return new Student(classroom);
    }
}
