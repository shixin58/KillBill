package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.demon.R;
import com.bride.demon.VolleyWrapper;

/**
 * <p>Created by shixin on 2019/3/22.
 */
public class VolleyActivity extends BaseActivity {

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, VolleyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_string:
                VolleyWrapper.getInstance().executeGetString();
                break;
            case R.id.tv_post_string:
                VolleyWrapper.getInstance().executePostString();
                break;
            case R.id.tv_post_json:
                VolleyWrapper.getInstance().executePostJson();
                break;
            case R.id.image:
                VolleyWrapper.getInstance().executeImage(findViewById(R.id.iv_demo));
                break;
            case R.id.image1:
                VolleyWrapper.getInstance().executeImageCache(findViewById(R.id.iv_demo1));
                break;
            case R.id.image2:
                VolleyWrapper.getInstance().executeNetworkImageView(findViewById(R.id.iv_demo2));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyWrapper.getInstance().cancel("xyz");
    }
}
