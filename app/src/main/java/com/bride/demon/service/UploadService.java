package com.bride.demon.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.bride.demon.activity.UploadActivity;

import androidx.annotation.Nullable;

/**
 * <p>Created by shixin on 2018/10/10.
 */
public class UploadService extends IntentService {

    public static final String KEY_NAME = "key_name";
    public static final String ACTION_UPLOAD = "action_upload";

    public static void openService(Context context, String name) {
        Intent intent = new Intent(context, UploadService.class);
        intent.setAction(ACTION_UPLOAD);
        intent.putExtra(KEY_NAME, name);
        context.startService(intent);
    }

    public UploadService() {
        super("UploadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("UploadService", "onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i("UploadService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("UploadService", "onDestroy");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(TextUtils.equals(ACTION_UPLOAD, intent.getAction())) {
            String name = intent.getStringExtra(KEY_NAME);

            // 上传中...
            SystemClock.sleep(5*1000);

            Intent intent1 = new Intent();
            intent1.setAction(UploadActivity.UPLOAD_RESULT);
            intent1.putExtra(KEY_NAME, name);
            sendBroadcast(intent1);
        }
    }
}
