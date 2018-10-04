// IMyService.aidl
package com.bride.demon;

// Declare any non-default types here with import statements
import com.bride.demon.User;
import com.bride.demon.Form;

interface IMyService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String getValue();

    int add(int a, int b);

    User getUser();

    void sendForm(in Form f);
}