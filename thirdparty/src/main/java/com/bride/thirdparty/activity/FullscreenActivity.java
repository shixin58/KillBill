package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bride.thirdparty.databinding.ActivityFullscreenBinding;
import com.bride.ui_lib.BaseActivity;
import com.bride.baselib.ui.OrientationUtils;

/**
 * <p>Created by shixin on 2019-04-26.
 */
public class FullscreenActivity extends BaseActivity {
    private static final String TAG = FullscreenActivity.class.getSimpleName();

    private ActivityFullscreenBinding mBinding;

    private OrientationUtils mOrientationUtils;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, FullscreenActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        mBinding = ActivityFullscreenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
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
        if (v == mBinding.tvTitle) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mOrientationUtils.setPortraitDefault();
            } else {
                mOrientationUtils.setLandscapeDefault();
            }
        } else {
            throw new IllegalStateException("未接收点击事件!");
        }
    }
}
