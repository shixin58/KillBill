package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bride.ui_lib.BaseActivity;
import com.bride.demon.R;

public class SingleTopActivity extends BaseActivity {
    private static final String TAG = SingleTopActivity.class.getSimpleName();

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, SingleTopActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate "+getTaskId()+" "+hashCode());
        setContentView(R.layout.activity_single_top);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent "+getTaskId()+" "+hashCode());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy "+getTaskId()+" "+hashCode());
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
