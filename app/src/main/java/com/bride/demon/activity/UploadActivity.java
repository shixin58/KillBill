package com.bride.demon.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bride.demon.R;
import com.bride.demon.UploadService;

/**
 * <p>Created by shixin on 2018/10/10.
 */
public class UploadActivity extends AppCompatActivity {

    public static String UPLOAD_RESULT = "upload_result";

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(TextUtils.equals(UPLOAD_RESULT, intent.getAction())) {
                String name = intent.getStringExtra(UploadService.KEY_NAME);
                Toast.makeText(context, name + " finishes upload", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, UploadActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UPLOAD_RESULT);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void onClick(View view) {
        String name = "talent";
        UploadService.openService(this, name);
        Toast.makeText(this, name + " starts upload", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}