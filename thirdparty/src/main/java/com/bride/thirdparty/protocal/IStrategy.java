package com.bride.thirdparty.protocal;

import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.Log;

/**
 * <p>Created by shixin on 2018/9/7.
 */
public interface IStrategy {
    String TAG = IStrategy.class.getSimpleName();

    void execute();

    @RequiresApi(api = Build.VERSION_CODES.N)
    default void fuckYou() {
        // 支持language level 1.8, API level 24
        Log.i(TAG, "fuckYou - Yeah!");
    }
}
