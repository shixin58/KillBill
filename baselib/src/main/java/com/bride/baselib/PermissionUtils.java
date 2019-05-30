package com.bride.baselib;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ADD_VOICEMAIL;
import static android.Manifest.permission.ANSWER_PHONE_CALLS;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.USE_SIP;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * <p>Created by shixin on 2018/12/8.
 */
public class PermissionUtils {
    private static final String TAG = PermissionUtils.class.getSimpleName();
    private static Context CONTEXT;
    public static void setContext(Context context) {
        CONTEXT = context.getApplicationContext();
    }

    public static void requestStoragePermission(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        if(ContextCompat.checkSelfPermission(CONTEXT, READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, READ_EXTERNAL_STORAGE)) {
                Toast.makeText(activity, "您需要到系统设置里打开存储权限", Toast.LENGTH_SHORT).show();
            }else {
                ActivityCompat.requestPermissions(activity, new String[]{READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE}, requestCode);
            }
        }
    }

    public static void requestPhonePermission(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        if(ContextCompat.checkSelfPermission(CONTEXT, READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, READ_PHONE_STATE)) {
                Toast.makeText(activity, "您需要到系统设置里打开读取手机信息权限", Toast.LENGTH_SHORT).show();
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ActivityCompat.requestPermissions(activity, new String[]{READ_PHONE_STATE, READ_PHONE_NUMBERS,
                            CALL_PHONE, ANSWER_PHONE_CALLS, ADD_VOICEMAIL, USE_SIP}, requestCode);
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{READ_PHONE_STATE,
                            CALL_PHONE, ADD_VOICEMAIL, USE_SIP}, requestCode);
                }
            }
        }
    }

    public static void requestAllPermissions(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        if(ContextCompat.checkSelfPermission(CONTEXT, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(CONTEXT, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(CONTEXT, CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{READ_EXTERNAL_STORAGE, READ_PHONE_STATE, CAMERA}, requestCode);
        }
    }
}
