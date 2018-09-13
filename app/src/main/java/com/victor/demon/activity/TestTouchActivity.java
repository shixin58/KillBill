package com.victor.demon.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.max.baselib.BaseActivity;
import com.victor.demon.widgets.MyViewGroup;

import org.jetbrains.annotations.Nullable;

/**
 * <p>Created by shixin on 2018/9/13.
 */
public class TestTouchActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        MyViewGroup viewGroup = new MyViewGroup(this);
        viewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
