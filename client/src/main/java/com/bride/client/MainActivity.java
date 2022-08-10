package com.bride.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bride.baselib.PermissionUtils;
import com.bride.baselib.module.ActivityNameFinals;
import com.bride.baselib.module.ActivitySchemas;
import com.bride.baselib.module.PackageNameFinals;
import com.bride.client.activity.BinderActivity;
import com.bride.client.activity.ClassLoaderActivity;
import com.bride.client.databinding.ActivityMainBinding;
import com.bride.ui_lib.BaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Created by shixin on 2018/9/4.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String UPLOAD_RESULT = "upload_result";
    public static final String KEY_NAME = "key_name";

    private MyRegisteredReceiver registeredReceiver;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.setActivity(this);
        initView();
        initData();
        PermissionUtils.requestAllPermissions(this, 1);
        Log.i(TAG, "onCreate "+getTaskId()+" "+hashCode());
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(registeredReceiver);
        unregisterReceiver(innerBroadcastReceiver);
        Log.i(TAG, "onDestroy "+getTaskId()+" "+hashCode());
        super.onDestroy();
    }

    private void initView() {
    }

    private void initData() {
        // 动态注册broadcast
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UPLOAD_RESULT);
        registerReceiver(innerBroadcastReceiver, intentFilter);

        registeredReceiver = new MyRegisteredReceiver();
        IntentFilter registeredIntentFilter = new IntentFilter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registeredIntentFilter.addAction(Intent.ACTION_USER_UNLOCKED);
            registeredIntentFilter.addAction(Intent.ACTION_LOCKED_BOOT_COMPLETED);
        }
        registeredIntentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        registeredIntentFilter.addAction(Intent.ACTION_REBOOT);
        registerReceiver(registeredReceiver, registeredIntentFilter);
    }

    public void onJumpClick(View v) {
        Intent intent;
        if (v==mBinding.tvActionView) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String urlString = new ActivitySchemas(ActivitySchemas.TOUCH_SCHEMA).setParam("type", 0).getUriString();
            intent.setData(Uri.parse(urlString));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } else if (v==mBinding.tvImplicit) {
            intent = new Intent();
            intent.setAction(ActivityNameFinals.App.PLATFORM);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(intent);
        } else if (v==mBinding.tvApp) {
            String name = PackageNameFinals.APP + ("debug".equals(BuildConfig.BUILD_TYPE)?".debug":"");
            if(checkPackageInfo(name)) {
                intent = getPackageManager().getLaunchIntentForPackage(name);
                startActivity(intent);
            } else {
                Toast.makeText(this, "未安装 - "+name, Toast.LENGTH_SHORT).show();
            }
        } else if (v==mBinding.tvComponent) {
            try {
                intent = new Intent();
                String packageName = PackageNameFinals.APP + ("debug".equals(BuildConfig.BUILD_TYPE)?".debug":"");
                intent.setClassName(packageName, ActivityNameFinals.App.GLIDE);
                startActivity(intent);
            } catch (Exception e) {
                // java.lang.SecurityException: Permission Denial: starting Intent { cmp=com.bride.demon.debug/com.bride.demon.activity.GlideActivity } from ProcessRecord{dabbcc9 10979:com.bride.client.debug/u0a241} (pid=10979, uid=10241) not exported from uid 10248
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v==mBinding.tvStartService) {
            try {
                intent = new Intent();
                intent.setClassName("com.bride.demon" + (BuildConfig.DEBUG ? ".debug":""),
                        "com.bride.demon.service.UploadService");
                intent.setAction("action_upload");
                intent.putExtra(KEY_NAME, "Beauty");
                startService(intent);
            } catch (Exception e) {
                // java.lang.SecurityException: Not allowed to start service Intent { act=action_upload cmp=com.bride.demon.debug/com.bride.demon.service.UploadService (has extras) } without permission not exported from uid 10248
                e.printStackTrace();
            }
        } else if (v==mBinding.tvOpenMusic) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("max://devil/music?from="+getPackageName()));
            startActivity(intent);
        } else if (v==mBinding.tvSendBroadcast) {
            // 静态广播+跨进程发广播
            // Broadcast所在进程com.roy.devil.debug必须运行
            intent = new Intent();
            intent.setAction("com.roy.devil.action.MUSIC");
            // 若不设置包名，提示"BroadcastQueue: Background execution not allowed: receiving Intent"
            intent.setPackage("com.roy.devil"+(BuildConfig.DEBUG?".debug":""));
            sendBroadcast(intent);
        } else if (v==mBinding.tvOpenBinder) {
            BinderActivity.openActivity(this);
        }
    }

    public void onOtherClick(View v) {
        if (v==mBinding.tvChangeThread) {
            transferWorkThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10_000L);
                        transferMainThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("UIThread", "更新UI");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (v==mBinding.tvExecuteTask) {
            // 小于核心线程数，直接创建新线程
            executorService.execute(()->{
                final String msg = "pid = "+Process.myPid()+"; tid = "+Process.myTid();
                getHandler().post(()->
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show()
                );
            });
        } else if (v==mBinding.tvPlugin) {
            ClassLoaderActivity.openActivity(this);
        }
    }

    private boolean checkPackageInfo(String name) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(name, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    private final BroadcastReceiver innerBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(TextUtils.equals(UPLOAD_RESULT, intent.getAction())) {
                String name = intent.getStringExtra(KEY_NAME);
                Toast.makeText(context, name + " finishes upload", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void transferMainThread(Runnable runnable) {
//              View#post(Runnable)
//              View#postDelayed(Runnable, long)
//              View#postInvalidate();
//              Activity#runOnUiThread(Runnable);
//              Handler#post(Runnable);
        // Message.target一直持有Handler，直到执行Message.recycle()
        Message message = Message.obtain(getHandler(), runnable);
        message.what = 1;
        getHandler().sendMessageAtTime(message, SystemClock.uptimeMillis() + 10 * 1000);
    }

    // 切换工作线程
    private void transferWorkThread(Runnable runnable) {
        executorService.execute(runnable);
    }

    ExecutorService executorService = new ThreadPoolExecutor(3, 5,
            1L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128));

    static class MyRegisteredReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                Log.d("MyRegisteredReceiver", action);
            }
        }
    }
}
