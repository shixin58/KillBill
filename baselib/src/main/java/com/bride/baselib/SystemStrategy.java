package com.bride.baselib;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.system.Os;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
        // 设备启动时间，不包含deep sleep
        // Handler#sendMessageDelayed
        System.out.println("SystemClock.uptimeMillis() "+SystemClock.uptimeMillis());

        // 设备启动时间
        System.out.println("SystemClock.elapsedRealtime() "+SystemClock.elapsedRealtime());
        System.out.println("SystemClock.elapsedRealtimeNanos() "+SystemClock.elapsedRealtimeNanos());

        // 当前线程启动时间
        System.out.println("SystemClock.currentThreadTimeMillis() "+SystemClock.currentThreadTimeMillis());
    }

    // SecurityException: getUniqueDeviceId: The user 10780 does not meet the requirements to access device identifiers
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

    public static String getAndroidId() {
        return Settings.Secure.getString(CONTEXT.getContentResolver(), Settings.Secure.ANDROID_ID);
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
//        inputStream();

        /*try {
            int result = System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        printTime();
    }

    private static void printTime() {
        // System.currentTimeMillis()保存当前时刻距1970年1月1日0点时刻的毫秒数，不随时区变化。
        // UTC时间，依赖系统时钟。设置当前日期、时间时，取系统默认时区，也可手动设置时区。
        // Date()、Timer#schedule(TimerTask, long, long)依赖System.currentTimeMillis()。
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        System.out.println("北京时间: "+sdf.format(date));
        sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        System.out.println("洛杉矶时间："+sdf.format(date));

        // 设备启动时间，不包含deep sleep，计算间隔时间用；
        // 纳秒nano，one billionth of a second, 10^-9, micro-benchmark具有更好的分辨率
        System.out.println("System.nanoTime() "+System.nanoTime());
    }
}
