package com.bride.demon.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.bride.demon.Form;
import com.bride.demon.IMyService;
import com.bride.demon.User;

import androidx.annotation.Nullable;

/**
 * 在主进程或外部进程bindService，在主进程主线程执行Service
 * <p>Created by shixin on 2018/9/8.
 */
public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    public static class MyServiceImpl extends IMyService.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.i("MyServiceImpl", Thread.currentThread().getName()+" basicTypes() "+anInt+"_"+aLong+"_"+aBoolean+"_"+aFloat+"_"+aDouble+"_"+aString);
        }

        @Override
        public String getValue() throws RemoteException {
            Log.i("MyServiceImpl", Thread.currentThread().getName()+" getValue()");
            return "终于等到你";
        }

        @Override
        public int add(int a, int b) throws RemoteException {
            Log.i("MyServiceImpl", Thread.currentThread().getName()+" add()");
            return a+b;
        }

        @Override
        public User getUser() throws RemoteException {
            Log.i("MyServiceImpl", Thread.currentThread().getName()+" getUser() ");
            return new User("Max", "137", Process.myPid()+"");
        }

        @Override
        public void sendForm(Form f) throws RemoteException {
            Log.i("MyServiceImpl", Thread.currentThread().getName()+" sendForm() "+f.toString());
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return new MyServiceImpl();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "onRebind");
    }
}
