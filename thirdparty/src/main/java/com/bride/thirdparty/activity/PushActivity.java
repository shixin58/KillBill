package com.bride.thirdparty.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bride.ui_lib.BaseActivity;
import com.bride.thirdparty.PushReceiver;
import com.bride.thirdparty.R;

/**
 * <p>Created by shixin on 2018/11/27.
 */
public class PushActivity extends BaseActivity {
    public static final String ACTION_TEST_MESSAGE = "action_test_message";

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
                startAlarm();
                break;
            default:
                break;
        }
    }

    private void startAlarm() {
        cancelAlarm();
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000,
                5000, getOperationIntent());
    }

    private void cancelAlarm() {
        mAlarmManager.cancel(getOperationIntent());
    }

    private PendingIntent getOperationIntent() {
        Intent intent = new Intent(this, PushReceiver.class);
        intent.setAction(ACTION_TEST_MESSAGE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
