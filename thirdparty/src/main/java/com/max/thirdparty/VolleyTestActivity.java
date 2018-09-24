package com.max.thirdparty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.max.baselib.BaseActivity;
import com.max.thirdparty.Strategy.VolleyStrategy;
import com.max.thirdparty.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * <p>Created by shixin on 2018/9/21.
 */
public class VolleyTestActivity extends BaseActivity {
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, VolleyTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_test);
        initView();
    }

    private void initView() {
        VolleyStrategy volleyStrategy = new VolleyStrategy();
        volleyStrategy.execute();
        volleyStrategy.executeImage((ImageView) findViewById(R.id.iv_demo));
        volleyStrategy.executeImage3((NetworkImageView) findViewById(R.id.iv_demo2));
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getInstance().getRequestQueue().cancelAll("xyz");
    }

    public void onClick(View view) {
        EventBus.getDefault().postSticky(new MessageEvent());
    }
}
