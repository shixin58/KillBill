package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.R;
import com.bride.thirdparty.Strategy.RxJavaStrategy;

import org.jetbrains.annotations.Nullable;

/**
 * <p>Created by shixin on 2019/1/16.
 */
public class RxJavaActivity extends BaseActivity {

    private RxJavaStrategy mRxJavaStrategy;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RxJavaActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        mRxJavaStrategy = new RxJavaStrategy();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_base:
                mRxJavaStrategy.execute();
                break;
            case R.id.tv_map:
                mRxJavaStrategy.executeMap();
                break;
            case R.id.tv_flatmap:
                mRxJavaStrategy.executeFlatMap();
                break;
            case R.id.tv_take:
                mRxJavaStrategy.executeTake();
                break;
            case R.id.tv_lift:
                mRxJavaStrategy.executeLift();
                break;
            default:
                break;
        }
    }
}
