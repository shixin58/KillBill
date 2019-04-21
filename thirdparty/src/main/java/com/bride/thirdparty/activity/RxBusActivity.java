package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.R;
import com.bride.thirdparty.bean.MessageEvent;
import com.bride.thirdparty.util.RxBus;

import org.greenrobot.eventbus.EventBus;

/**
 * <p>Created by shixin on 2019-04-21.
 */
public class RxBusActivity extends BaseActivity {

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RxBusActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_post_sticky:
                EventBus.getDefault().postSticky(new MessageEvent("Direwolf is really loyal!"));
                break;
            case R.id.tv_rxbus_post:
                RxBus.getInstance().post(new MessageEvent("RxBus is awesome!"));
                break;
        }
    }
}
