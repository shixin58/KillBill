package com.bride.ui_lib;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.Fragment;

import timber.log.Timber;

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
        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.tag(TAG).d("onDestroy");
    }

    protected Handler getHandler() {
        return mHandler;
    }
}
