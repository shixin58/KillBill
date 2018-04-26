package com.victor.demon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * <p>Created by shixin on 2018/4/26.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // Activity实现了Factory2接口
        return super.onCreateView(parent, name, context, attrs);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);
        super.onCreate(savedInstanceState);
    }
}
