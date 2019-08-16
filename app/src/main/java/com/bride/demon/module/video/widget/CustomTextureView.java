package com.bride.demon.module.video.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

/**
 * <p>Created by shixin on 2019-08-16.
 */
public class CustomTextureView extends TextureView {
    private static final String TAG = CustomTextureView.class.getSimpleName();

    public CustomTextureView(Context context) {
        super(context);
    }

    public CustomTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout");
    }

    // Texture#onDraw空实现，且final
    @Override public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        Log.i(TAG, "onDrawForeground");
    }
}
