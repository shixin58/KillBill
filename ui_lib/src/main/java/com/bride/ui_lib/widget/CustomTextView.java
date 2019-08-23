package com.bride.ui_lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * <p>Created by shixin on 2019-07-31.
 */
public class CustomTextView extends AppCompatTextView {
    private static final String TAG = CustomTextView.class.getSimpleName();

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, hashCode()+"# onMeasure "+MeasureSpec.getMode(widthMeasureSpec)+" "+MeasureSpec.getSize(widthMeasureSpec)
                +" "+MeasureSpec.getMode(heightMeasureSpec)+" "+MeasureSpec.getSize(heightMeasureSpec));
        // WRAP_CONTENT: UNSPECIFIED 1560
        // 40dp: EXACTLY 120
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, hashCode()+"# onLayout "+changed+" "+left+" "+top+" "+right+" "+bottom);
    }
}
