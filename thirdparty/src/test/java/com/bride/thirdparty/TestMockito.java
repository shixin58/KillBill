package com.bride.thirdparty;

import com.bride.thirdparty.bean.Phone;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * <p>Created by shixin on 2018/9/10.
 */
public class TestMockito {

    @Before public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @After public void recycle() {
        System.out.println("recycle");
    }

    @Test public void isCorrect() {
        Assert.assertEquals(4, 6-2);
    }

    @Test public void testGetScreen() {

        Phone phone = Mockito.mock(Phone.class);

        Mockito.when(phone.getScreen()).thenReturn("Retina").thenReturn("Cornea");

        String result = phone.getScreen()+" "+phone.getScreen();

        Mockito.verify(phone, Mockito.times(2)).getScreen();

        Assert.assertEquals("Retina Cornea", result);
    }
}
