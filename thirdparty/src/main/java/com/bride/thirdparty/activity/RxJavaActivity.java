package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bride.ui_lib.BaseActivity;
import com.bride.thirdparty.R;
import com.bride.thirdparty.strategy.RxJavaStrategy;

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
            case R.id.tv_just:
                RxJavaStrategy.getInstance().executeJust();
                break;
            case R.id.tv_base:
                RxJavaStrategy.getInstance().executeObservable();
                break;
            case R.id.tv_single:
                RxJavaStrategy.getInstance().executeSingle();
                break;
            case R.id.tv_completable:
                RxJavaStrategy.getInstance().executeCompletable();
                break;
            case R.id.tv_maybe:
                RxJavaStrategy.getInstance().executeMaybe();
                break;
            case R.id.tv_concat:
                RxJavaStrategy.getInstance().executeConcat();
                break;
            case R.id.tv_merge:
                RxJavaStrategy.getInstance().executeMerge();
                break;
            case R.id.tv_backpressure:
                RxJavaStrategy.getInstance().executeFlowable();
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
            case R.id.tv_interval:
                RxJavaStrategy.getInstance().executeInterval();
                break;
            case R.id.tv_timer:
                RxJavaStrategy.getInstance().executeTimer();
                break;
            case R.id.tv_range:
                RxJavaStrategy.getInstance().executeRange();
                break;
            case R.id.tv_zip:
                RxJavaStrategy.getInstance().executeZip();
                break;
            case R.id.tv_combineLatest:
                RxJavaStrategy.getInstance().executeCombineLatest();
                break;
            case R.id.tv_reduce:
                RxJavaStrategy.getInstance().executeReduce();
                break;
            case R.id.tv_collect:
                RxJavaStrategy.getInstance().executeCollect();
                break;
            case R.id.tv_count:
                RxJavaStrategy.getInstance().executeCount();
                break;
            case R.id.tv_startWith:
                RxJavaStrategy.getInstance().executeStartWith();
                break;
            case R.id.tv_lift:
                RxJavaStrategy.getInstance().executeLift(findViewById(R.id.iv_demo1), findViewById(R.id.iv_demo2));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        RxJavaStrategy.getInstance().onDestroy("RxJava");
        super.onDestroy();
    }
}
