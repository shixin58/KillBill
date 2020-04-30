package com.bride.widget.activity;

import android.graphics.Typeface;
import android.os.Bundle;
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
}
