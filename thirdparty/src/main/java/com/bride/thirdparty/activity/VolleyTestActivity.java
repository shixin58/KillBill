package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.R;
import com.bride.thirdparty.Strategy.URLConnectionStrategy;
import com.bride.thirdparty.Strategy.VolleyStrategy;
import com.bride.thirdparty.ThirdPartyApplication;
import com.bride.thirdparty.bean.MessageEvent;
import com.bride.thirdparty.util.RxBus;

import org.greenrobot.eventbus.EventBus;

/**
 * <p>Created by shixin on 2019/3/22.
 */
public class VolleyTestActivity extends BaseActivity {
    private VolleyStrategy volleyStrategy = new VolleyStrategy();

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, VolleyTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_test);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_string:
                volleyStrategy.executeGetString();
                break;
            case R.id.tv_post_string:
                volleyStrategy.executePostString();
                break;
            case R.id.tv_post_json:
                volleyStrategy.executePostJson();
                break;
            case R.id.image:
                volleyStrategy.executeImage(findViewById(R.id.iv_demo));
                break;
            case R.id.image1:
                volleyStrategy.executeImageCache(findViewById(R.id.iv_demo1));
                break;
            case R.id.image2:
                volleyStrategy.executeNetworkImageView(findViewById(R.id.iv_demo2));
                break;
            case R.id.tv_urlconnection_get:
                URLConnectionStrategy.get();
                break;
            case R.id.tv_urlconnection_post:
                URLConnectionStrategy.post();
                break;
            case R.id.tv_post_sticky:
                EventBus.getDefault().postSticky(new MessageEvent("Direwolf is really loyal!"));
                break;
            case R.id.tv_rxbus_post:
                RxBus.getInstance().post(new MessageEvent("RxBus is awesome!"));
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        ThirdPartyApplication.getInstance().getRequestQueue().cancelAll("xyz");
    }
}
