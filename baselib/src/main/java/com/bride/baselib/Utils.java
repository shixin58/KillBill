package com.bride.baselib;

import android.os.Environment;

/**
 * <p>Created by shixin on 2019-04-25.
 */
public class Utils {

    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
