package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bride.thirdparty.databinding.ActivityRetrofitBinding;
import com.bride.thirdparty.strategy.RetrofitKtStrategy;
import com.bride.ui_lib.BaseActivity;
import com.bride.thirdparty.strategy.RetrofitStrategy;

/**
 * <p>Created by shixin on 2019/1/16.
 */
public class RetrofitActivity extends BaseActivity {

    private ActivityRetrofitBinding mBinding;
    RetrofitStrategy mRetrofitStrategy;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RetrofitActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRetrofitBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.setActivity(this);

        mRetrofitStrategy = new RetrofitStrategy();
    }

    public void onClick(View view) {
        if (view==mBinding.tvNetwork) {
            mRetrofitStrategy.execute();
        } else if (view==mBinding.tvRxjava) {
            mRetrofitStrategy.executeRxJava();
        } else if (view==mBinding.tvAsynctask) {
            mRetrofitStrategy.testAsyncTask();
        } else if (view==mBinding.tvSuspend) {
            RetrofitKtStrategy.INSTANCE.getPhoneInfo();
        } else if (view==mBinding.tvSuspend2) {
            RetrofitKtStrategy.INSTANCE.getPhoneInfo2();
        } else if (view==mBinding.tvSuspend3) {
            RetrofitKtStrategy.INSTANCE.testSuspend();
        } else if (view==mBinding.tvSuspend4) {
            RetrofitKtStrategy.INSTANCE.testSuspend2();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRetrofitStrategy.onDestroy();
    }
}
