package com.bride.ui_lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * <p>Created by shixin on 2019-07-31.
 */
public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("CustomTextView", MeasureSpec.getMode(heightMeasureSpec)+" "+MeasureSpec.getSize(heightMeasureSpec));
        // WRAP_CONTENT: UNSPECIFIED 1560
        // 40dp: EXACTLY 120
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
