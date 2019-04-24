package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.demon.R;

/**
 * <p>Created by shixin on 2019-04-24.
 */
public class StandardActivity extends BaseActivity {

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, StandardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate "+getTaskId());
        setContentView(R.layout.activity_standard);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent "+getTaskId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy "+getTaskId());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_standard:
                StandardActivity.openActivity(this);
                break;
            case R.id.tv_single_top:
                SingleTopActivity.openActivity(this);
                break;
            case R.id.tv_single_task:
                SingleTaskActivity.Companion.openActivity(this);
                break;
            case R.id.tv_single_instance:
                SingleInstanceActivity.Companion.openActivity(this);
                break;
        }
    }
}
