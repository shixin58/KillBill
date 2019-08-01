package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bride.ui_lib.BaseActivity;
import com.bride.baselib.ResUtils;
import com.bride.baselib.ui.ViewUtils;
import com.bride.thirdparty.R;

/**
 * <p>Created by shixin on 2018/11/27.
 */
public class LandscapeActivity extends BaseActivity {
    private static final String TAG = LandscapeActivity.class.getSimpleName();

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LandscapeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate "+getTaskId()+" "+hashCode());
        setContentView(R.layout.activity_landscape);
        initView();
    }

    private void initView() {
        TextView tvHeight = findViewById(R.id.tv_height);
        String source = ResUtils.getString(R.string.get_height);
        ViewUtils.setTypeface(tvHeight);
        ViewUtils.testHeight(tvHeight, source);
        ViewUtils.testLineCount(source);
        ViewUtils.testTextPaint(source);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent "+getTaskId()+" "+hashCode());
    }

    @Override
    public void recreate() {
        super.recreate();
        Log.i(TAG, "recreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy "+getTaskId()+" "+hashCode());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recreate:
                recreate();
                break;
            case R.id.tv_eventbus:
                EventBusActivity.openActivity(this);
                break;
        }
    }
}
