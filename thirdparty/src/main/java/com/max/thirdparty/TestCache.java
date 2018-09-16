package com.max.thirdparty;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * <p>Created by shixin on 2018/9/16.
 */
public class TestCache implements IContextStrategy {
    @Override
    public void execute(Context context) {
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
        // /storage/emulated/0/Android/data/com.max.thirdparty/cache
        Log.i("getExternalCacheDir", ""+context.getExternalCacheDir());
        // /storage/emulated/0/Android/data/com.max.thirdparty/files/Music
        Log.i("getExternalFilesDir", ""+context.getExternalFilesDir(Environment.DIRECTORY_MUSIC));

        // 不可见，卸载app删除
        // /data/data/com.max.thirdparty/cache
        Log.i("getCacheDir", ""+context.getCacheDir());
        // /data/data/com.max.thirdparty/files
        Log.i("getFilesDir", ""+context.getFilesDir());
    }
}
