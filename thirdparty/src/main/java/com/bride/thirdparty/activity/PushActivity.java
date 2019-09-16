package com.bride.thirdparty.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bride.thirdparty.R;
import com.bride.ui_lib.BaseActivity;

/**
 * <p>Created by shixin on 2018/11/27.
 */
public class PushActivity extends BaseActivity {
    public static final String ACTION_TEST_MESSAGE = "com.bride.thirdparty.broadcast.ACTION_TEST_MESSAGE";

    private AlarmManager mAlarmManager;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, PushActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        mAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAlarm();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_push:
                Toast.makeText(this, "发广播 - "+ACTION_TEST_MESSAGE, Toast.LENGTH_SHORT).show();
                startAlarm();
                break;
            default:
                break;
        }
    }

    private void startAlarm() {
        cancelAlarm();
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10 * 1000,
                60 * 1000, getOperationIntent());
    }

    private void cancelAlarm() {
        mAlarmManager.cancel(getOperationIntent());
    }

    private PendingIntent getOperationIntent() {
        Intent intent = new Intent();
        intent.setAction(ACTION_TEST_MESSAGE);
        // 指定接收app
        intent.setPackage(this.getPackageName());
        // 指定接收器
//        intent.setClass(this, PushReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
