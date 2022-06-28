package com.bride.baselib.lru;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.bride.baselib.ui.ImageUtils;

/**
 * 自定义Volley图片请求内存缓存。
 * <p>使用双检锁单例。内部用LruCache实现。
 * <p>Created by shixin on 2018/9/16.
 */
public class BitmapCache implements ImageLoader.ImageCache {
    private final LruCache<String, Bitmap> mLruCache;

    private static volatile BitmapCache mBitmapCache;

    private BitmapCache() {
        // 获取app最大可用内存
        long maxMemory = Runtime.getRuntime().maxMemory();
        Log.i("maxMemory", maxMemory / (1024 * 1024f)+"MB");

        mLruCache = new LruCache<String, Bitmap>(64 * 1024 * 1024) {// 64MB
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return ImageUtils.getBitmapSize(value);
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
        return mLruCache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        mLruCache.put(s, bitmap);
    }
}
