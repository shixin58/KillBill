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
    private Context mContext;
    public CacheStrategy(Context context) {
        mContext = context;
    }

    @Override
    public void execute() {
        // 内部存储
        // /system
        Log.i("getRootDirectory", ""+Environment.getRootDirectory());
        // /data
        Log.i("getDataDirectory", ""+Environment.getDataDirectory());
        // /cache
        Log.i("DownloadCache", ""+Environment.getDownloadCacheDirectory());

        // 外部存储，可见，卸载app不删除
        // /storage/emulated/0
        Log.i("ExternalStorage", ""+Environment.getExternalStorageDirectory());
        // /storage/emulated/0/Music
        Log.i("ExternalStoragePublic", ""+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));

        // 外部存储，可见，卸载app删除
        // /storage/emulated/0/Android/data/com.bride.thirdparty/cache
        Log.i("getExternalCacheDir", ""+mContext.getExternalCacheDir());
        // /storage/emulated/0/Android/data/com.bride.thirdparty/files/Music
        Log.i("getExternalFilesDir", ""+mContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC));

        // 不可见，卸载app删除
        // /data/data/com.bride.thirdparty/cache
        Log.i("getCacheDir", ""+mContext.getCacheDir());
        // /data/data/com.bride.thirdparty/files
        Log.i("getFilesDir", ""+mContext.getFilesDir());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fuckYou();
        }
    }
}
