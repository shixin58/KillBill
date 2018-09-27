package com.victor.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    // 包访问权限
    int type;

    public static void openActivity(Context context, int type) {
        Intent intent = new Intent(context, TestTouchActivity.class);
        // mExtras.putInt(String, int)
        // Intent、ComponentName、Bundle均实现Parcelable接口，bind进程通信传递Parcelable对象
        // intent.getExtras()内部new Bundle(mExtras)为了保护数据吧
        // Intent和Bundle设置的跟获取的均不同
        intent.putExtra("type", type);
        Log.i("TestTouchActivity", "openActivity-"+intent.hashCode());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_touch);
        initView();
    }

    private void initView() {
        Log.i("TestTouchActivity", "onCreate-"+getIntent().hashCode());
        // mExtras.getInt(String, int)
        this.type = getIntent().getIntExtra("type", 0);
        MyViewGroup viewGroup;
        if(type==1) {
            viewGroup = new MyViewGroup(this);
        }else {
            viewGroup = findViewById(R.id.my_view_group);
        }
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
