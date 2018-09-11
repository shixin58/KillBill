package com.max.thirdparty;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Created by shixin on 2018/9/11.
 */
public class TestJunit {

    @Test public void testSetScreen() {
        Phone phone = new Phone("Retina");
        Assert.assertEquals("Retina", phone.getScreen());
        phone.setScreen("Cornea");
        Assert.assertEquals("Cornea", phone.getScreen());

//        phone.setScreen(null);
        Assert.assertNotNull("为null", phone.getScreen());

        phone.setScreen("cute");
        Assert.assertTrue("太短", phone.getScreen().length()<10);

//        Assert.fail("整体到局部");
    }
}