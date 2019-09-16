package com.bride.thirdparty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.bride.thirdparty.activity.PushActivity;

/**
 * <p>Created by shixin on 2018/11/27.
 */
public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || TextUtils.isEmpty(intent.getAction())) return;
        switch (intent.getAction()) {
            case PushActivity.ACTION_TEST_MESSAGE:
                Toast.makeText(context, "收到推送 "+intent.getAction(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
