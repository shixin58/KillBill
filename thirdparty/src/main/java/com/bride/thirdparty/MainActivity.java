package com.bride.thirdparty;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bride.thirdparty.databinding.ActivityMainBinding;
import com.bride.ui_lib.BaseActivity;
import com.bride.baselib.PermissionUtils;
import com.bride.thirdparty.activity.DirectoryActivity;
import com.bride.thirdparty.activity.EventBusActivity;
import com.bride.thirdparty.activity.FullscreenActivity;
import com.bride.thirdparty.activity.LandscapeActivity;
import com.bride.thirdparty.activity.PushActivity;
import com.bride.thirdparty.activity.RetrofitActivity;
import com.bride.thirdparty.activity.RxBusActivity;
import com.bride.thirdparty.activity.RxJavaActivity;
import com.bride.thirdparty.activity.UrlConnectionActivity;
import com.bride.thirdparty.bean.MessageEvent;
import com.bride.thirdparty.util.RxBus;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.Subject;

/**
 * <p>Created by shixin on 2018/9/7.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ActivityMainBinding mBinding;

    private Subject<MessageEvent> mSubject;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initView();
        initData();
    }

    private void initView() {
        mBinding.tvEventbus.setOnClickListener(this);
        mBinding.tvLandscape.setOnClickListener(this);
        mBinding.tvFullscreen.setOnClickListener(this);
        mBinding.tvPush.setOnClickListener(this);
        mBinding.tvRetrofit.setOnClickListener(this);
        mBinding.tvRxjava.setOnClickListener(this);
        mBinding.tvDirectory.setOnClickListener(this);
        mBinding.tvRxBus.setOnClickListener(this);
        mBinding.tvUrlConnection.setOnClickListener(this);
    }

    private void initData() {
        // 1、测试RxBus
        mSubject = RxBus.getInstance().register(MessageEvent.class);
        // Consumer和Observer均可使用
        mDisposable = mSubject.observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageEvent ->
                        Toast.makeText(MainActivity.this, messageEvent.info, Toast.LENGTH_SHORT).show());

        // 2、请求系统权限
        PermissionUtils.requestAllPermissions(this, 3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
        RxBus.getInstance().unregister(MessageEvent.class, mSubject);
    }

    @Override
    public void onClick(View v) {
        if (v==mBinding.tvEventbus) {
            EventBusActivity.openActivity(getApplication());
        } else if (v==mBinding.tvLandscape) {
            LandscapeActivity.openActivity(this);
        } else if (v==mBinding.tvFullscreen) {
            FullscreenActivity.openActivity(this);
        } else if (v==mBinding.tvPush) {
            PushActivity.openActivity(this);
        } else if (v==mBinding.tvRetrofit) {
            RetrofitActivity.openActivity(this);
        } else if (v==mBinding.tvRxjava) {
            RxJavaActivity.openActivity(this);
        } else if (v==mBinding.tvDirectory) {
            DirectoryActivity.openActivity(this);
        } else if (v==mBinding.tvRxBus) {
            RxBusActivity.openActivity(this);
        } else if (v==mBinding.tvUrlConnection) {
            UrlConnectionActivity.openActivity(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 3) {}
    }
}
