package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bride.baselib.BaseActivity;
import com.bride.baselib.Urls;
import com.bride.demon.DemonApplication;
import com.bride.demon.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.RunnableFuture;

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
        CustomViewTarget<ImageView, Bitmap> target = new CustomViewTarget<ImageView, Bitmap>(imageView) {
            @Override
            protected void onResourceCleared(Drawable placeholder) {
                Log.i(TAG, "CustomViewTarget#onResourceCleared");
            }

            @Override
            public void onLoadFailed(Drawable errorDrawable) {
                Log.i(TAG, "CustomViewTarget#onLoadFailed");
            }

            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                Log.i(TAG, "CustomViewTarget#onResourceReady");
                view.setImageBitmap(resource);
            }

            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG, "CustomViewTarget#onStart");
            }

            @Override
            public void onStop() {
                super.onStop();
                Log.i(TAG, "CustomViewTarget#onStop");
            }

            @Override
            public void onDestroy() {
                super.onDestroy();
                Log.i(TAG, "CustomViewTarget#onDestroy");
            }
        };
        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                Log.i(TAG, "RequestListener#onLoadFailed");
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                Log.i(TAG, "RequestListener#onResourceReady");
                return false;
            }
        };
        Glide.with(this)
                .asBitmap()
                .load(Urls.Images.LOGO)
                .listener(requestListener)
                .into(target);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear_memory_cache:
                Glide.get(this).clearMemory();
                Toast.makeText(DemonApplication.Companion.getInstance(),
                        "清空Glide内存缓存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_clear_disk_cache:
                DemonApplication.Companion.getInstance().getExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(GlideActivity.this).clearDiskCache();
                        GlideActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DemonApplication.Companion.getInstance(),
                                        "清空Glide磁盘缓存成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
        }
    }
}
