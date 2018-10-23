package com.bride.thirdparty.Strategy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import android.system.Os;
import android.telephony.TelephonyManager;

import com.bride.thirdparty.MyApplication;
import com.bride.thirdparty.protocal.IStrategy;

import java.util.Locale;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * <p>Created by shixin on 2018/10/7.
 */
public class SystemStrategy implements IStrategy {
    @Override
    public void execute() {
        // UTC时间，依赖系统时钟，设置当前日期、时间用
        System.out.println("System.currentTimeMillis() "+System.currentTimeMillis());
        // 0代表设备启动时间，不包含deep sleep，计算间隔时间用
        System.out.println("System.nanoTime() "+System.nanoTime());

        // 当前线程启动时间
        System.out.println("SystemClock.currentThreadTimeMillis() "+SystemClock.currentThreadTimeMillis());
        // 自设备启动时间
        System.out.println("SystemClock.elapsedRealtime() "+SystemClock.elapsedRealtime());
        System.out.println("SystemClock.elapsedRealtimeNanos() "+SystemClock.elapsedRealtimeNanos());
        // 设备启动时间，不包含deep sleep
        System.out.println("SystemClock.uptimeMillis() "+SystemClock.uptimeMillis());

        System.out.println("getDeviceInfo "+getDeviceInfo());
        testProcessInfo();
    }

    public static String getDeviceId(Activity activity) {
        if(ContextCompat.checkSelfPermission(MyApplication.getInstance(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {

            }else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
            return "Unknown";
        }
        TelephonyManager telephonyManager = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        if(telephonyManager == null) {
            return "Unknown";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint({"MissingPermission", })  String imei = telephonyManager.getImei();
            @SuppressLint("MissingPermission") String meid = telephonyManager.getMeid();
            System.out.println("imei: "+imei+"; meid: "+meid);
        }
        @SuppressLint("MissingPermission") String deviceId = telephonyManager.getDeviceId();
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
    public static void testProcessInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println("Process: "+Process.myPid()+"|"+Process.myTid()+"|"+Process.myUid()+"|"+Process.is64Bit());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            System.out.println("Os: "+Os.getpid()+"|"+Os.getppid()+"|"+Os.gettid()+"|"+Os.getuid());
        }
    }
}
