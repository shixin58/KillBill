package com.victor.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.max.baselib.BaseActivity;
import com.victor.demon.R;
import com.victor.demon.widget.MyViewGroup;

import org.jetbrains.annotations.Nullable;

/**
 * <p>Created by shixin on 2018/9/13.
 */
public class TestTouchActivity extends BaseActivity {
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, TestTouchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_touch);
        initView();
    }

    private void initView() {
        MyViewGroup viewGroup = findViewById(R.id.my_view_group);
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
