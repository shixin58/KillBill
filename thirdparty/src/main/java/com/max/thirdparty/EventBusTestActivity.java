package com.max.thirdparty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.max.baselib.BaseActivity;
import com.max.thirdparty.bean.MessageEvent;

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

    public void onClick(View v) {
        EventBus.getDefault().post(new MessageEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onMessageEvent(MessageEvent event) {
        Log.i("onMessageEvent", "Cool");
        EventBus.getDefault().removeStickyEvent(event);
    }
}
