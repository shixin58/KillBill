package com.bride.thirdparty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * <p>Created by shixin on 2018/9/21.
 */
public class EventBusTestActivity extends BaseActivity {
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, EventBusTestActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_test);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onMessageClick(View v) {
        // 在View构造器中拿到AttributeSet，解析布局文件中为View设置的属性，根据属性值对View做相应设置
        // View实例化时解析android:onClick属性，自动为View设置点击监听器，利用属性值通过反射找到Method并绑定，点击时执行
        EventBus.getDefault().post(new MessageEvent("bug"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onMessageEvent(MessageEvent event) {
        Log.i("onMessageEvent", "Cool");
        EventBus.getDefault().removeStickyEvent(event);
    }
}
