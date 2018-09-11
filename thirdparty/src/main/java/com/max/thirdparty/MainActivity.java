package com.max.thirdparty;

import android.os.Bundle;

import com.max.baselib.BaseActivity;

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
        new RxJavaStrategy().execute();
    }
}
