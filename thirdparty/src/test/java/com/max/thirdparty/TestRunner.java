package com.max.thirdparty;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * <p>Created by shixin on 2018/9/11.
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestJunit.class);
        for(Failure failure:result.getFailures()) {
            System.out.println(failure);
        }
        System.out.println("wasSuccessful "+result.wasSuccessful());
    }
}
