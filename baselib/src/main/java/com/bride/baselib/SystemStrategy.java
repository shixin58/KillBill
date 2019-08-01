package com.bride.baselib;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;
import android.system.Os;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * <p>Created by shixin on 2018/10/7.
 */
public class SystemStrategy {
    private static final String TAG = SystemStrategy.class.getSimpleName();
    private static Context CONTEXT;

    public static void setContext(Context context) {
        CONTEXT = context.getApplicationContext();
    }

    public void execute() {
        // UTC时间，依赖系统时钟，设置当前日期、时间用
        System.out.println("System.currentTimeMillis() "+System.currentTimeMillis());
        // 0代表设备启动时间，不包含deep sleep，计算间隔时间用；
        // 纳秒nano，one billionth of a second, 10^-9, micro-benchmark具有更好的分辨率
        System.out.println("System.nanoTime() "+System.nanoTime());

        // 当前线程启动时间
        System.out.println("SystemClock.currentThreadTimeMillis() "+SystemClock.currentThreadTimeMillis());
        // 自设备启动时间
        System.out.println("SystemClock.elapsedRealtime() "+SystemClock.elapsedRealtime());
        System.out.println("SystemClock.elapsedRealtimeNanos() "+SystemClock.elapsedRealtimeNanos());
        // 设备启动时间，不包含deep sleep
        System.out.println("SystemClock.uptimeMillis() "+SystemClock.uptimeMillis());
    }

    public static String getDeviceId() {
        if(ContextCompat.checkSelfPermission(CONTEXT, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            return "Unknown";
        }
        TelephonyManager telephonyManager = (TelephonyManager) CONTEXT.getSystemService(Context.TELEPHONY_SERVICE);
        if(telephonyManager == null) {
            return "Unknown";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint({"MissingPermission", })
            String imei = telephonyManager.getImei();
            @SuppressLint("MissingPermission")
            String meid = telephonyManager.getMeid();
            Log.i(TAG, "imei: "+imei+"; meid: "+meid);
        }
        @SuppressLint("MissingPermission")
        String deviceId = telephonyManager.getDeviceId();
        if(deviceId != null && deviceId.trim().length()>0) {
            return deviceId;
        }else {
            return "Unknown";
        }
    }

    public static String getDeviceInfo() {
        // HUAWEI|BKL-AL20|HONOR|BKL-AL20|BKL|HWBKL|A7Q0217B30000225|26|8.0.0|zh
        return Build.MANUFACTURER
                +"|"+Build.PRODUCT
                +"|"+Build.BRAND
                +"|"+Build.MODEL
                +"|"+Build.BOARD
                +"|"+Build.DEVICE
                +"|"+Build.SERIAL/* 手机序列号，adb命令行指定设备 */
                +"|"+Build.VERSION.SDK_INT
                +"|"+Build.VERSION.RELEASE
                +"|"+Locale.getDefault().getLanguage();/* 判断手机系统语言，设置手机默认语言 */
    }

//    @SuppressLint("NewApi")
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void printProcessInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "Process: "+Process.myPid()+"|"+Process.myTid()+"|"+Process.myUid()+"|"+Process.is64Bit());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.i(TAG, "Os: "+Os.getpid()+"|"+Os.getppid()+"|"+Os.gettid()+"|"+Os.getuid());
        }
    }

    public static void inputStream() {
        try {
            System.err.println("Type message and press enter");
            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.out.println("You typed "+str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static void main(String[] args) {
        inputStream();
        /*try {
            int result = System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
