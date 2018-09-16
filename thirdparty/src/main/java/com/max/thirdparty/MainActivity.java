package com.max.thirdparty;

import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
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
//        new RxJavaStrategy().execute();
//        new VolleyStrategy().execute(this, (ImageView) findViewById(R.id.iv_demo));
//        new VolleyStrategy().execute(this, (NetworkImageView) findViewById(R.id.iv_demo2));
        new TestCache().execute(this);
    }
}
