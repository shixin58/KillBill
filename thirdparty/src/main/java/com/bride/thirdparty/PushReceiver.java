package com.bride.thirdparty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * <p>Created by shixin on 2018/11/27.
 */
public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "收到推送 "+intent.getAction(), Toast.LENGTH_SHORT).show();
    }
}
