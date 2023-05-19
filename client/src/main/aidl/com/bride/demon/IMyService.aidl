// IMyService.aidl
package com.bride.demon;

// Declare any non-de fault types here with import statements
import com.bride.demon.model.User;
import com.bride.demon.model.Form;

interface IMyService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    oneway void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String getValue();

    int add(int a, int b);

    User getUser();

    oneway void sendForm(in Form f);

    void connect();

    void disconnect();

    boolean isConnected();
}