package com.max.thirdparty;

import android.os.Bundle;
import android.widget.ImageView;

import com.max.baselib.BaseActivity;
import com.max.thirdparty.Strategy.VolleyStrategy;

/**
 * <p>Created by shixin on 2018/9/7.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        VolleyStrategy volleyStrategy = new VolleyStrategy();
        volleyStrategy.execute();
        volleyStrategy.executeImage((ImageView) findViewById(R.id.iv_demo));

        /*RetrofitStrategy retrofitStrategy = new RetrofitStrategy();
        retrofitStrategy.execute();
        retrofitStrategy.executeRxJava();*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getInstance().getRequestQueue().cancelAll("xyz");
    }
}
