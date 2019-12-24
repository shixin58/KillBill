package com.bride.widget.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bride.ui_lib.BaseActivity;
import com.bride.widget.R;

/**
 * <p>Created by shixin on 2019-12-24.
 */
public class LottieActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LottieActivity.class);
        context.startActivity(intent);
    }
}
