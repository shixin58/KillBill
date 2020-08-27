package com.bride.thirdparty.activity;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bride.thirdparty.R;
import com.bride.ui_lib.BaseActivity;

import java.util.List;

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

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }

        getNotchParams();

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

    /**
     * 获得刘海区域信息
     */
    @TargetApi(Build.VERSION_CODES.P)
    public void getNotchParams() {
        final View decorView = getWindow().getDecorView();
        decorView.post(() -> {
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            DisplayCutout displayCutout = windowInsets.getDisplayCutout();
            // 当全屏顶部显示黑边时，getDisplayCutout()返回为null
            if (displayCutout != null) {
                Log.e("notch", "SafeInset[ " + displayCutout.getSafeInsetLeft() + ", " + displayCutout.getSafeInsetTop() + ", " + displayCutout.getSafeInsetRight() + ", " + displayCutout.getSafeInsetBottom() + "]");
                setNotch(displayCutout.getSafeInsetLeft(), displayCutout.getSafeInsetTop(), displayCutout.getSafeInsetRight(), displayCutout.getSafeInsetBottom());
                // 获得刘海区域
                List<Rect> rectList = displayCutout.getBoundingRects();
                if (rectList.isEmpty()) {
                    Log.e("notch", "not notch");
                } else {
                    Log.e("notch", "size:" + rectList.size());
                    for (Rect rect : rectList) {
                        Log.e("notch", "notch area：" + rect);
                    }
                }
            }
        });
    }

    public void setNotch(int safeInsetLeft, int safeInsetTop, int safeInsetRight, int safeInsetBottom) {
        if (safeInsetTop > 0) {
            View tvPush = findViewById(R.id.tv_push);
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) tvPush.getLayoutParams();
            lp.topMargin = safeInsetTop;
            tvPush.setLayoutParams(lp);
        }
    }
}
