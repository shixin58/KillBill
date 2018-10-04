package com.bride.thirdparty;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * <p>Created by shixin on 2018/9/11.
 */
public class TestJunit2 extends TestCase {

    @Override
    protected TestResult createResult() {
        return super.createResult();
    }

    @Override
    public void run(TestResult result) {
        super.run(result);
        System.out.println("TestJunit2 run");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.out.println("TestJunit2 setUp");
    }

    public void testAdd() {
        System.out.println(getName());
        setName("TestJunit2 testAdd");
        System.out.println(getName());

        int variable = 5;
        Assert.assertEquals(5, variable);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        System.out.println("TestJunit2 tearDown");
    }
}