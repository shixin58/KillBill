package com.bride.baselib.ui;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.bride.baselib.ResUtils;

/**
 * <p>Created by shixin on 2019-04-26.
 */
public class ViewUtils {

    private static final String TAG = ViewUtils.class.getSimpleName();

    public static void testHeight(TextView textView, String source) {
        textView.setText(source);
        textView.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "View#getWidth/getHeight - ["+textView.getWidth()+" * "+textView.getHeight()+"]");
            }
        });
    }

    // 用StaticLayout求TextView行数
    public static void testLineCount(String source) {
        TextPaint textPaint = new TextPaint();
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, ResUtils.getDisplayMetrics());
        textPaint.setTextSize(textSize);
        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, ResUtils.getDisplayMetrics());
        StaticLayout staticLayout;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            staticLayout = StaticLayout.Builder.obtain(source, 0, source.length(), textPaint, (int) width)
                    .build();
        }else {
            staticLayout = new StaticLayout(source, textPaint, (int) width,
                    Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        }
        Log.i(TAG, "Layout#getHeight - "+staticLayout.getLineCount()+" - "+staticLayout.getHeight());
    }

    public static void testTextPaint(String source) {
        TextPaint textPaint = new TextPaint();
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, ResUtils.getDisplayMetrics());
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.DEFAULT);

        Rect rect = new Rect();
        textPaint.getTextBounds(source, 0, source.length(), rect);
        Log.i(TAG, "testTextPaint - ["+rect.width()+" * "+rect.height()+"] - ["+textPaint.ascent()+", "+textPaint.descent()+"]");

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        Log.i(TAG, "FontMetrics - ["+fontMetrics.top+", "+fontMetrics.bottom+"] - ["
                +fontMetrics.ascent+", "+fontMetrics.descent+"] - "+fontMetrics.leading);

        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        Log.i(TAG, "FontMetricsInt - ["+fontMetricsInt.top+", "+fontMetricsInt.bottom+"] - ["
                +fontMetricsInt.ascent+", "+fontMetricsInt.descent+"] - "+fontMetricsInt.leading);

        Log.i(TAG, "measureText - "+textPaint.measureText(source, 0, source.length()));
    }

    public static void setTypeface(TextView textView) {
        Typeface typeface = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/custom_font.TTF");
        textView.setTypeface(typeface);
    }
}
