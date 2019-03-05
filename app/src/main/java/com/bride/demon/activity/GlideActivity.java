package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bride.baselib.BaseActivity;
import com.bride.demon.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.Nullable;

/**
 * <p>Created by shixin on 2019/3/5.
 */
public class GlideActivity extends BaseActivity {
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, GlideActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        initView();
    }

    private void initView() {
        ImageView imageView = findViewById(R.id.iv_logo);
        Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);
    }
}
