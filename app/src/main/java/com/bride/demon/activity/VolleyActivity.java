package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bride.baselib.KotlinUtilsKt;
import com.bride.baselib.net.UrlParams;
import com.bride.baselib.net.Urls;
import com.bride.baselib.net.VolleyWrapper;
import com.bride.demon.R;
import com.bride.ui_lib.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created by shixin on 2019/3/22.
 */
public class VolleyActivity extends BaseActivity {
    private static final String TAG = VolleyActivity.class.getSimpleName();

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
                UrlParams urlParams = new UrlParams(Urls.JUHE_MOBILE).put("phone", "13701116418").put("key", Urls.JUHE_KEY);
                VolleyWrapper.getInstance().getString(urlParams, new VolleyWrapper.Callback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(TAG, "executeGetString - onResponse - "+response);
                        KotlinUtilsKt.toast(response);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        KotlinUtilsKt.toast(e.getMessage());
                    }
                }, VolleyActivity.this);
                break;
            case R.id.tv_post_string:
                Map<String, String> params = new HashMap<>();
                params.put("phone", "13701116418");
                params.put("key", Urls.JUHE_KEY);
                VolleyWrapper.getInstance().postString(Urls.JUHE_MOBILE, params, new VolleyWrapper.Callback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(TAG, "executePostString - onResponse - "+response);
                        KotlinUtilsKt.toast(response);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        KotlinUtilsKt.toast(e.getMessage());
                    }
                }, VolleyActivity.this);
                break;
            case R.id.tv_post_json:
                Map<String, String> postmanParams = new HashMap<>();
                postmanParams.put("phone", "13701116418");
                postmanParams.put("key", Urls.JUHE_KEY);
                VolleyWrapper.getInstance().executePostJson(Urls.POSTMAN_POST, postmanParams, new VolleyWrapper.Callback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(TAG, "executePostJson - onResponse - "+response);
                        Toast.makeText(VolleyActivity.this, response, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        Toast.makeText(VolleyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }, VolleyActivity.this);
                break;
        }
    }

    public void onImageClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                VolleyWrapper.getInstance().executeImage(findViewById(R.id.iv_demo), Urls.Images.BEAUTY);
                break;
            case R.id.image1:
                VolleyWrapper.getInstance().executeImageCache(findViewById(R.id.iv_demo1), Urls.Images.BEAUTY, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyWrapper.getInstance().cancel(VolleyActivity.this);
    }
}
