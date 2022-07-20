package com.bride.ui_lib;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.fragment.app.Fragment;

/**
 * <p>Created by shixin on 2019/3/22.
 */
public class BaseFragment extends Fragment {
    protected static String TAG;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    {
        TAG = getClass().getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    protected Handler getHandler() {
        return mHandler;
    }
}
