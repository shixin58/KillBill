package com.bride.thirdparty;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestResult;

/**
 * <p>Created by shixin on 2018/9/11.
 */
public class MyTestResult extends TestResult {
    @Override
    public synchronized void addError(Test test, Throwable t) {
        super.addError(test, t);
        System.out.println("TestJunit3 addError");
    }

    @Override
    public synchronized void addFailure(Test test, AssertionFailedError t) {
        super.addFailure(test, t);
        System.out.println("TestJunit3 addFailure");
    }

    @Override
    public synchronized void stop() {
        super.stop();
        System.out.println("TestJunit3 stop");
    }
}
