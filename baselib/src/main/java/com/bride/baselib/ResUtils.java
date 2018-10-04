package com.bride.baselib;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class ResUtils {

    private static Context context;

    public static void setContext(Context context) {
        ResUtils.context = context;
    }

    public static String getString(int id) {
        return context.getString(id);
    }

    public static String[] getStringArray(int id) {
        return context.getResources().getStringArray(id);
    }

    public static int getColor(int id) {
        return context.getResources().getColor(id);
    }

    public static ColorStateList getColorStateList(int id) {
        return context.getResources().getColorStateList(id);
    }

    public static Drawable getDrawable(int resId) {
        return context.getResources().getDrawable(resId);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return context.getResources().getDisplayMetrics();
    }

    public static int dp2px(float dpValue) {
        float rs = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getDisplayMetrics());
        return (int) rs;
    }

    public static int sp2px(float spValue) {
        final float fontScale = getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
