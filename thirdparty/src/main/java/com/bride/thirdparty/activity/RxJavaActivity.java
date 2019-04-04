package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.R;
import com.bride.thirdparty.Strategy.RxJavaStrategy;

/**
 * <p>Created by shixin on 2019/1/16.
 */
public class RxJavaActivity extends BaseActivity {

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RxJavaActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_base:
                RxJavaStrategy.getInstance().execute();
                break;
            case R.id.tv_map:
                RxJavaStrategy.getInstance().executeMap();
                break;
            case R.id.tv_flatmap:
                RxJavaStrategy.getInstance().executeFlatMap();
                break;
            case R.id.tv_take:
                RxJavaStrategy.getInstance().executeTake();
                break;
            case R.id.tv_lift:
                RxJavaStrategy.getInstance().executeLift();
                break;
            default:
                break;
        }
    }
}
