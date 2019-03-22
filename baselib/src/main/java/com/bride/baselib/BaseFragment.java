package com.bride.baselib;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

/**
 * <p>Created by shixin on 2019/3/22.
 */
public class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate", getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy", getClass().getSimpleName());
    }
}
