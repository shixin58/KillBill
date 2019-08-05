package com.bride.ui_lib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridView;

/**
 * <p>Created by shixin on 2019-08-05.
 */
public class CustomGridView extends GridView {

    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("CustomGridView", hashCode()+"# onMeasure "+MeasureSpec.getMode(heightMeasureSpec)+" "+MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i("CustomGridView", hashCode()+"# onLayout "+changed+" "+l+" "+t+" "+r+" "+b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // onDraw调用特别频繁
        Log.i("CustomGridView", "onDraw");
    }
}
