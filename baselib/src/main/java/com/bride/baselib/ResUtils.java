package com.bride.baselib;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.List;

/**
 * <p>Created by shixin on 2018/4/1.
 */
public class ResUtils {
    private static final String TAG = ResUtils.class.getSimpleName();

    private static Context context;

    // 在Application#onCreate设置
    public static void setContext(Context context) {
        ResUtils.context = context.getApplicationContext();
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

    public static boolean isMainThread(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SERVICES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        String mainProcessName = packageInfo.applicationInfo.processName;
        int pId = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = null;
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        if (processInfos != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
                if (processInfo.pid == pId) {
                    runningAppProcessInfo = processInfo;
                }
            }
        }
        if (runningAppProcessInfo != null && TextUtils.equals(mainProcessName, runningAppProcessInfo.processName)) {
            return true;
        }
        return false;
    }
}
