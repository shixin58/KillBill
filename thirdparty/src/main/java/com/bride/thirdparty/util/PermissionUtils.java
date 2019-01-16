package com.bride.thirdparty.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import com.bride.thirdparty.ThirdPartyApplication;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.*;

/**
 * <p>Created by shixin on 2018/12/8.
 */
public class PermissionUtils {
    private static final String TAG = PermissionUtils.class.getSimpleName();

    public static void requestStoragePermission(Activity activity, int requestCode) {
        if(ContextCompat.checkSelfPermission(ThirdPartyApplication.getInstance(), READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, READ_EXTERNAL_STORAGE)) {
                Log.i(TAG, "shouldShowRequestPermissionRationale READ_EXTERNAL_STORAGE");
            }else {
                ActivityCompat.requestPermissions(activity, new String[]{READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE}, requestCode);
            }
        }
    }

    public static void requestPhonePermission(Activity activity, int requestCode) {
        if(ContextCompat.checkSelfPermission(ThirdPartyApplication.getInstance(), READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, READ_PHONE_STATE)) {
                Log.i(TAG, "shouldShowRequestPermissionRationale READ_PHONE_STATE");
            }else {
                ActivityCompat.requestPermissions(activity, new String[]{READ_PHONE_STATE, READ_PHONE_NUMBERS,
                        CALL_PHONE, ANSWER_PHONE_CALLS, ADD_VOICEMAIL, USE_SIP}, requestCode);
            }
        }
    }
}
