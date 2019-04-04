package com.bride.thirdparty.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * <p>Created by shixin on 2018/9/16.
 */
public class BitmapCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> mLruCache;

    private static volatile BitmapCache mBitmapCache;

    private BitmapCache() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        Log.i("maxMemory", maxMemory/(1024*1024f)+"MB");

        mLruCache = new LruCache<String, Bitmap>(64*1024*1024) {// 64MB
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public static BitmapCache getInstance() {
        if (mBitmapCache == null) {
            synchronized (BitmapCache.class) {
                if (mBitmapCache == null) {
                    mBitmapCache = new BitmapCache();
                }
            }
        }
        return mBitmapCache;
    }

    @Override
    public Bitmap getBitmap(String s) {
        Bitmap bitmap = mLruCache.get(s);
        return bitmap;
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        mLruCache.put(s, bitmap);
    }
}
