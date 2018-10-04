package com.bride.demon;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * <p>Created by shixin on 2018/9/8.
 */
public class MyService extends Service {

    public class MyServiceImpl extends IMyService.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.i("MyServiceImpl", "basicTypes "+anInt+"_"+aLong+"_"+aBoolean+"_"+aFloat+"_"+aDouble+"_"+aString);
        }

        @Override
        public String getValue() throws RemoteException {
            return "终于等到你";
        }

        @Override
        public int add(int a, int b) throws RemoteException {
            return a+b;
        }

        @Override
        public User getUser() throws RemoteException {
            return new User("Max", "137", Process.myPid()+"");
        }

        @Override
        public void sendForm(Form f) throws RemoteException {
            Log.i("MyServiceImpl", "sendForm "+f.toString());
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyServiceImpl();
    }
}
