package com.bride.thirdparty.protocal;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * <p>Created by shixin on 2018/9/7.
 */
public interface IStrategy {

    void execute();

    @RequiresApi(api = Build.VERSION_CODES.N)
    default public void fuckYou() {
        // 支持language level 1.8, API level 24
        Log.i("fuckYou", "Yeah!");
    }
}
