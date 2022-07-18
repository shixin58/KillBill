package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.bride.demon.databinding.ActivityTouchBinding;
import com.bride.ui_lib.BaseActivity;

/**
 * ViewGroup#dispatchTouchEvent()利用touch坐标查找合适的child, 然后调用ViewGroup#dispatchTransformedTouchEvent。
 * <p>若有，执行child#dispatchTouchEvent(); 否则执行super#dispatchTouchEvent()。
 * <p>ViewGroup#dispatchTouchEvent()调用onTouch()和onTouchEvent()，onTouchEvent()调用performClick()。
 * <p>Created by shixin on 2018/9/13.
 */
public class TouchActivity extends BaseActivity {
    private static final String TAG = TouchActivity.class.getSimpleName();

    private ActivityTouchBinding mBinding;

    public static void openActivity(Context context, int type) {
        Intent intent = new Intent(context, TouchActivity.class);
        // mExtras.putInt(String, int)
        // Intent、ComponentName、Bundle均实现Parcelable接口，bind进程通信传递Parcelable对象
        // intent.getExtras()内部new Bundle(mExtras)为了保护数据吧
        // Intent和Bundle设置的跟获取的均不同
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTouchBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.setActivity(this);
        initView();
    }

    private void initView() {
        // 标记View实现方式，从外部传入。默认0从布局文件获取，1采取硬编码
        int type = getIntent().getIntExtra("type", 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent "+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent "+event.getAction());
        // 若ACTION_DOWN事件所有View均未消费，后续事件不再向下分发
        return super.onTouchEvent(event);
    }

    public void onClick(View view) {
        if (view==mBinding.tvDirection) {
            Log.i("TouchActivity", "onClick tvDirection");
        }
    }
}
