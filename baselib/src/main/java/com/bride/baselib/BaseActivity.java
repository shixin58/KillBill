package com.bride.baselib;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

/**
 * <p>Created by shixin on 2019/3/22.
 */
public class BaseActivity extends AppCompatActivity {
    protected static String TAG;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    {
        TAG = getClass().getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    protected Handler getHandler() {
        return mHandler;
    }
}
