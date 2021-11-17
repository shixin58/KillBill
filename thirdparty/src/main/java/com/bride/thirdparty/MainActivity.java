package com.bride.thirdparty;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

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
public class MainActivity extends BaseActivity implements View.OnClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Subject<MessageEvent> mSubject;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate "+getTaskId()+" "+hashCode());
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.tv_eventbus).setOnClickListener(this);
        findViewById(R.id.tv_landscape).setOnClickListener(this);
        findViewById(R.id.tv_fullscreen).setOnClickListener(this);
        findViewById(R.id.tv_push).setOnClickListener(this);
        findViewById(R.id.tv_retrofit).setOnClickListener(this);
        findViewById(R.id.tv_rxjava).setOnClickListener(this);
        findViewById(R.id.tv_directory).setOnClickListener(this);
        findViewById(R.id.tv_rx_bus).setOnClickListener(this);
        findViewById(R.id.tv_url_connection).setOnClickListener(this);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent "+getTaskId()+" "+hashCode());
    }

    @Override
    public void recreate() {
        super.recreate();
        Log.i(TAG, "recreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy "+getTaskId()+" "+hashCode());
        mDisposable.dispose();
        RxBus.getInstance().unregister(MessageEvent.class, mSubject);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_eventbus:
                EventBusActivity.openActivity(getApplication());
                break;
            case R.id.tv_landscape:
                LandscapeActivity.openActivity(this);
                break;
            case R.id.tv_fullscreen:
                FullscreenActivity.openActivity(this);
                break;
            case R.id.tv_push:
                PushActivity.openActivity(this);
                break;
            case R.id.tv_retrofit:
                RetrofitActivity.openActivity(this);
                break;
            case R.id.tv_rxjava:
                RxJavaActivity.openActivity(this);
                break;
            case R.id.tv_directory:
                DirectoryActivity.openActivity(this);
                break;
            case R.id.tv_rx_bus:
                RxBusActivity.openActivity(this);
                break;
            case R.id.tv_url_connection:
                UrlConnectionActivity.openActivity(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
            case 2:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult "+permissions[0]);
                }
                break;
            case 3:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult "+permissions[0]);
                }
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult "+permissions[1]);
                }
        }
    }
}
