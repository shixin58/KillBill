package com.bride.baselib;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

/**
 * <p>Created by shixin on 2018/5/11.
 */
public class ImageUtils {

    /**
     * 判断图片格式，十万纳秒级
     * @return image/webp or image/png
     */
    public static boolean isWebp(Resources resources, int resId) {
        long start = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ?
                SystemClock.elapsedRealtimeNanos() : SystemClock.elapsedRealtime();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        String type = options.outMimeType;
        boolean isWebp = TextUtils.equals(type, "image/webp");

        long end = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ?
                SystemClock.elapsedRealtimeNanos() : SystemClock.elapsedRealtime();
        Log.i("Victor", resources.getResourceEntryName(resId)+" isWebp "+isWebp+"; type "+type+"; interval "+(end - start));
        return isWebp;
    }

    /**
     * 判断图片格式，千纳秒级
     */
    public static boolean isWebp(String name) {
        long start = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ?
                SystemClock.elapsedRealtimeNanos() : SystemClock.elapsedRealtime();

        boolean isWebp = name.contains("skin" + "_" + "w" + "_");

        long end = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ?
                SystemClock.elapsedRealtimeNanos() : SystemClock.elapsedRealtime();
        Log.i("Victor", name+" isWebp "+isWebp+"; interval "+(end - start));
        return isWebp;
    }

    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }
}
