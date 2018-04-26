package com.victor.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.victor.demon.BaseActivity;
import com.victor.demon.R;

/**
 * <p>Created by shixin on 2018/4/26.
 */
public class BlankActivity extends BaseActivity {

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, BlankActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
    }
}
