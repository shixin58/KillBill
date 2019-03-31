package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.R;
import com.bride.thirdparty.Strategy.RetrofitStrategy;

/**
 * <p>Created by shixin on 2019/1/16.
 */
public class RetrofitActivity extends BaseActivity {

    RetrofitStrategy mRetrofitStrategy;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RetrofitActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        mRetrofitStrategy = new RetrofitStrategy();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_network:
                mRetrofitStrategy.execute();
                break;
            case R.id.tv_rxjava:
                mRetrofitStrategy.executeRxJava();
                break;
            case R.id.tv_asynctask:
                mRetrofitStrategy.testAsyncTask();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRetrofitStrategy.onDestroy();
    }
}
