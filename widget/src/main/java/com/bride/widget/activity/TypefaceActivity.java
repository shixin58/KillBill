package com.bride.widget.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.bride.ui_lib.BaseActivity;
import com.bride.widget.R;

/**
 * <p>Created by shixin on 2020/4/30.
 */
public class TypefaceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeface);

        TextView textView = findViewById(R.id.tv_brain);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Arial_Rounded_Bold.ttf");
        textView.setTypeface(tf);
    }

    private void setRoulette() {
        RotateAnimation ra = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(2000L);
        ra.setFillAfter(true);
        ra.setInterpolator(new LinearInterpolator());
        ra.setRepeatMode(Animation.RESTART);
        ra.setRepeatCount(Animation.INFINITE);
        findViewById(R.id.container_roulette).startAnimation(ra);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_brain:
                setRoulette();
                break;
        }
    }
}
