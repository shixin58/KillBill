package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.bride.baselib.BaseActivity;
import com.bride.demon.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <p>Created by shixin on 2019-07-09.
 */
public class ExceptionActivity extends BaseActivity {

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ExceptionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_stutter:
                // CPU Profiler中Sample Java Methods可查看方法调用栈和每个方法调用时长
                SystemClock.sleep(2000);
                Toast.makeText(this, "test stutter", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_anr:
                SystemClock.sleep(10000L);
                Toast.makeText(this, "test anr", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_null_pointer:
                String s = null;
                s.toCharArray();
                break;
            case R.id.tv_work_thread_exception:
                Executor executor = Executors.newCachedThreadPool();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        int i = 1/0;
                    }
                });
                break;
        }
    }
}
