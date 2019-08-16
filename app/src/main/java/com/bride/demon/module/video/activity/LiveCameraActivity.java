package com.bride.demon.module.video.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bride.demon.module.video.widget.CustomTextureView;
import com.bride.ui_lib.BaseActivity;
import com.bride.baselib.PermissionUtils;

import java.io.IOException;

import static android.Manifest.permission.CAMERA;

/**
 * <p>Created by shixin on 2019-05-30.
 */
public class LiveCameraActivity extends BaseActivity implements TextureView.SurfaceTextureListener {
    private static final String TAG = LiveCameraActivity.class.getSimpleName();

    private Camera mCamera;
    private TextureView mTextureView;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LiveCameraActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextureView = new CustomTextureView(this);
        mTextureView.setSurfaceTextureListener(this);
        setContentView(mTextureView);
    }

    @Override public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.i(TAG, "onSurfaceTextureAvailable");
        if (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED) {
            mCamera = Camera.open();
            try {
                mCamera.setPreviewTexture(surface);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            PermissionUtils.requestCameraPermission(this, 1);
        }
    }

    @Override public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.i(TAG, "onSurfaceTextureSizeChanged");
    }

    @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.i(TAG, "onSurfaceTextureDestroyed");
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        return true;
    }

    @Override public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//        Log.i(TAG, "onSurfaceTextureUpdated");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finish();
                    return;
                }
                if (mTextureView.getSurfaceTexture() != null) {
                    mCamera = Camera.open();
                    try {
                        mCamera.setPreviewTexture(mTextureView.getSurfaceTexture());
                        mCamera.startPreview();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
