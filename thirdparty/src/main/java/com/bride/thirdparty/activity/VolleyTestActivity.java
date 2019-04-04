package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.R;
import com.bride.thirdparty.Strategy.URLConnectionStrategy;
import com.bride.thirdparty.Strategy.VolleyStrategy;
import com.bride.thirdparty.bean.MessageEvent;
import com.bride.thirdparty.util.RxBus;

import org.greenrobot.eventbus.EventBus;

/**
 * <p>Created by shixin on 2019/3/22.
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
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_string:
                VolleyStrategy.getInstance().executeGetString();
                break;
            case R.id.tv_post_string:
                VolleyStrategy.getInstance().executePostString();
                break;
            case R.id.tv_post_json:
                VolleyStrategy.getInstance().executePostJson();
                break;
            case R.id.image:
                VolleyStrategy.getInstance().executeImage(findViewById(R.id.iv_demo));
                break;
            case R.id.image1:
                VolleyStrategy.getInstance().executeImageCache(findViewById(R.id.iv_demo1));
                break;
            case R.id.image2:
                VolleyStrategy.getInstance().executeNetworkImageView(findViewById(R.id.iv_demo2));
                break;
            case R.id.tv_urlconnection_get:
                URLConnectionStrategy.get();
                break;
            case R.id.tv_urlconnection_post:
                URLConnectionStrategy.post();
                break;
            case R.id.tv_urlconnection_get_image:
                URLConnectionStrategy.getImage(findViewById(R.id.iv_demo3));
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
    protected void onDestroy() {
        super.onDestroy();
        VolleyStrategy.getInstance().cancel("xyz");
    }
}
