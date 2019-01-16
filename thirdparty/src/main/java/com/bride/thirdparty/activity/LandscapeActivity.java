package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.R;

import org.jetbrains.annotations.Nullable;

/**
 * <p>Created by shixin on 2018/11/27.
 */
public class LandscapeActivity extends BaseActivity {
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LandscapeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Victor", "onCreate "+savedInstanceState);
        setContentView(R.layout.activity_landscape);
        testHeight();
    }

    // 用StaticLayout求TextView行数
    private void testHeight() {
        TextView tvHeight = findViewById(R.id.tv_height);
        String source = getResources().getString(R.string.get_height);
        tvHeight.setText(source);
        tvHeight.post(new Runnable() {
            @Override
            public void run() {
                Log.i("View#getHeight", tvHeight.getHeight()+"");
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TextPaint textPaint = new TextPaint();
            float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
            textPaint.setTextSize(textSize);
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
            StaticLayout staticLayout = StaticLayout.Builder.obtain(source, 0, source.length(), textPaint, (int) width)
                    .build();
            Log.i("Layout#getHeight", staticLayout.getLineCount()+"-"+staticLayout.getHeight());
        }else {
            TextPaint textPaint = new TextPaint();
            float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
            textPaint.setTextSize(textSize);
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
            StaticLayout staticLayout = new StaticLayout(source, textPaint, (int) width,
                    Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
            Log.i("Layout#getHeight", staticLayout.getLineCount()+"-"+staticLayout.getHeight());
        }
    }
}
