package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bride.ui_lib.BaseActivity;
import com.bride.baselib.ui.OrientationUtils;
import com.bride.thirdparty.R;

/**
 * <p>Created by shixin on 2019-04-26.
 */
public class FullscreenActivity extends BaseActivity {
    private static final String TAG = FullscreenActivity.class.getSimpleName();

    private OrientationUtils mOrientationUtils;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, FullscreenActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_fullscreen);
        mOrientationUtils = new OrientationUtils(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOrientationUtils.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mOrientationUtils.end();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mOrientationUtils.onConfigurationChanged(newConfig.orientation);
        Log.i(TAG, "onConfigurationChanged - "+newConfig.orientation);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mOrientationUtils.setPortraitDefault();
                } else {
                    mOrientationUtils.setLandscapeDefault();
                }
                break;
            default:
                throw new IllegalStateException("未接收点击事件!");
        }
    }
}
