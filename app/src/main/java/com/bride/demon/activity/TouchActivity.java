package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.demon.R;
import com.bride.demon.widget.CustomViewGroup;

/**
 * <p>Created by shixin on 2018/9/13.
 */
public class TouchActivity extends BaseActivity {
    private static final String TAG = TouchActivity.class.getSimpleName();

    int type;

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
        setContentView(R.layout.activity_touch);
        initView();
        Log.i(TAG, "onCreate "+getTaskId()+" "+hashCode());
    }

    private void initView() {
        this.type = getIntent().getIntExtra("type", 0);
        CustomViewGroup viewGroup = type == 1 ? new CustomViewGroup(this) : findViewById(R.id.my_view_group);

        // ViewGroup#dispatchTouchEvent利用touch坐标查找合适的child, 然后调用ViewGroup#dispatchTransformedTouchEvent。
        // 若有，执行child#dispatchTouchEvent; 否则执行super#dispatchTouchEvent。
        // ViewGroup#dispatchTouchEvent调用onTouch和onTouchEvent，onTouchEvent调用performClick。
        viewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "onTouch "+event.getAction());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return false;
            }
        });
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick");
                viewGroup.setBackgroundColor(Color.BLUE);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent "+getTaskId()+" "+hashCode());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy "+getTaskId()+" "+hashCode());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent "+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent "+event.getAction());
        return super.onTouchEvent(event);
    }
}
