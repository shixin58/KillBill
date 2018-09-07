package com.max.client;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.max.baselib.BaseActivity;

import org.jetbrains.annotations.Nullable;

/**
 * <p>Created by shixin on 2018/9/4.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_title).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title:
                // app进程间调用页面
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("max://demon/recycler.view"));
                startActivity(intent);
                break;
        }
    }
}
