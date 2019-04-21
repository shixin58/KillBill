package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.R;
import com.bride.thirdparty.Strategy.URLConnectionStrategy;

/**
 * <p>Created by shixin on 2019-04-21.
 */
public class UrlConnectionActivity extends BaseActivity {

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, UrlConnectionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_connection);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_urlconnection_get:
                URLConnectionStrategy.get();
                break;
            case R.id.tv_urlconnection_post:
                URLConnectionStrategy.post();
                break;
            case R.id.tv_urlconnection_get_image:
                URLConnectionStrategy.getImage(findViewById(R.id.iv_demo3));
                break;
        }
    }
}
