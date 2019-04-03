package com.bride.thirdparty.Strategy;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.bride.thirdparty.protocal.IStrategy;

/**
 * <p>Created by shixin on 2018/9/16.
 */
public class CacheStrategy implements IStrategy {
    private static final String TAG = CacheStrategy.class.getSimpleName();

    private Context mContext;
    public CacheStrategy(Context context) {
        mContext = context;
    }

    @Override
    public void execute() {
        // 内部存储
        // /system
        Log.i(TAG, "Environment#getRootDirectory - "+Environment.getRootDirectory());
        // /data
        Log.i(TAG, "Environment#getDataDirectory - "+Environment.getDataDirectory());
        // /cache
        Log.i(TAG, "Environment#getDownloadCacheDirectory - "+Environment.getDownloadCacheDirectory());

        // 外部存储，可见，卸载app不删除
        // /storage/emulated/0
        Log.i(TAG, "Environment#getExternalStorageDirectory - "+Environment.getExternalStorageDirectory());
        // /storage/emulated/0/Music
        Log.i(TAG, "Environment#getExternalStoragePublicDirectory - "
                +Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));

        // 外部存储，可见，卸载app删除
        // /storage/emulated/0/Android/data/com.bride.thirdparty/cache
        Log.i(TAG, "Context#getExternalCacheDir - "+mContext.getExternalCacheDir());
        // /storage/emulated/0/Android/data/com.bride.thirdparty/files/Music
        Log.i(TAG, "Context#getExternalFilesDir - "+mContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC));

        // 不可见，卸载app删除
        // /data/data/com.bride.thirdparty/cache
        Log.i(TAG, "Context#getCacheDir - "+mContext.getCacheDir());
        // /data/data/com.bride.thirdparty/files
        Log.i(TAG, "Context#getFilesDir - "+mContext.getFilesDir());

        Log.i(TAG, "isSDCardMounted - "+isSDCardMounted());

        testDefaultMethod();
    }

    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public void testDefaultMethod() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fuckYou();
        }
    }
}
