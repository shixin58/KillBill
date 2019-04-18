package com.bride.widget.activity;

import android.os.Bundle;

import com.bride.baselib.BaseActivity;
import com.bride.widget.MyLifecycleObserver;
import com.bride.widget.R;
import com.bride.widget.ui.main.BossFragment;

/**
 * <p>Created by shixin on 2019-04-18.
 */
public class BossActivity extends BaseActivity {

    private MyLifecycleObserver mLifecycleObserver = new MyLifecycleObserver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BossFragment.newInstance())
                    .commitNow();
        }
        getLifecycle().addObserver(mLifecycleObserver);
    }

    @Override
    protected void onDestroy() {
        getLifecycle().removeObserver(mLifecycleObserver);
        super.onDestroy();
    }
}
