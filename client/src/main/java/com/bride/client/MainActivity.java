package com.bride.client;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bride.baselib.ActivityNameFinals;
import com.bride.baselib.ActivitySchemas;
import com.bride.baselib.BaseActivity;
import com.bride.baselib.PackageNameFinals;
import com.bride.demon.Form;
import com.bride.demon.IMyService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Created by shixin on 2018/9/4.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static String UPLOAD_RESULT = "upload_result";
    public static final String KEY_NAME = "key_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void initView() {
        findViewById(R.id.tv_action_view).setOnClickListener(this);
        findViewById(R.id.tv_implicit).setOnClickListener(this);
        findViewById(R.id.tv_app).setOnClickListener(this);
        findViewById(R.id.tv_component).setOnClickListener(this);

        findViewById(R.id.tv_switch).setOnClickListener(this);
        findViewById(R.id.tv_use_service).setOnClickListener(this);
        findViewById(R.id.tv_start_service).setOnClickListener(this);
        findViewById(R.id.tv_open_music).setOnClickListener(this);
        findViewById(R.id.tv_send_broadcast).setOnClickListener(this);

        findViewById(R.id.tv_changeThread).setOnClickListener(this);
        findViewById(R.id.tv_execute_task).setOnClickListener(this);
    }

    private void initData() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UPLOAD_RESULT);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_action_view:
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String urlString = new ActivitySchemas(ActivitySchemas.TEST_TOUCH_SCHEMA).setParam("type", 0).getUriString();
                intent.setData(Uri.parse(urlString));
                startActivity(intent);
                break;
            case R.id.tv_implicit:
                intent = new Intent();
                intent.setAction("com.bride.demon.activity.TestFragmentActivity");
                intent.addCategory("android.intent.category.DEFAULT");
                startActivity(intent);
                break;
            case R.id.tv_app:
                String name = PackageNameFinals.APP + ("debug".equals(BuildConfig.BUILD_TYPE)?".debug":"");
                if(checkPackageInfo(name)) {
                    intent = getPackageManager().getLaunchIntentForPackage(name);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "未安装 - "+name, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_component:
                try {
                    intent = new Intent();
                    String packageName = PackageNameFinals.APP + ("debug".equals(BuildConfig.BUILD_TYPE)?".debug":"");
                    intent.setClassName(packageName, ActivityNameFinals.App.GLIDE);
                    startActivity(intent);
                } catch (Exception e) {
                    // java.lang.SecurityException: Permission Denial: starting Intent { cmp=com.bride.demon.debug/com.bride.demon.activity.GlideActivity } from ProcessRecord{dabbcc9 10979:com.bride.client.debug/u0a241} (pid=10979, uid=10241) not exported from uid 10248
                    e.printStackTrace();
                }
                break;
            case R.id.tv_switch:
                if(mService == null) {
                    try {
                        // 启动服务并拿到服务代理。服务所在进程必须运行
                        intent = new Intent();
                        intent.setClassName("com.bride.demon" + (BuildConfig.DEBUG ? ".debug":""),
                                "com.bride.demon.service.MyService");
                        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                    } catch (Exception e) {
                        // java.lang.SecurityException: Not allowed to bind to service Intent { cmp=com.bride.demon.debug/com.bride.demon.service.MyService }
                        e.printStackTrace();
                    }
                }else {
                    unbindService(mServiceConnection);
                }
                break;
            case R.id.tv_use_service:
                if(mService != null) {
                    try {
                        mService.basicTypes(1, 10L, true, 2.3f, 9.99, "Jacob");
                        Log.i("IMyService", mService.getUser().toString());
                        Log.i("IMyService", "add "+mService.add(1, 2));
                        Log.i("IMyService", mService.getValue());
                        mService.sendForm(new Form("洛杉矶", "1988"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.tv_start_service:
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
                break;
            case R.id.tv_open_music:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("max://devil/music?from="+getPackageName()));
                startActivity(intent);
                break;
            case R.id.tv_send_broadcast:
                // Broadcast所在进程必须运行
                intent = new Intent();
                intent.setAction("com.roy.devil.action.MUSIC");
                // 若不设置包名，提示"BroadcastQueue: Background execution not allowed: receiving Intent"
                intent.setPackage("com.roy.devil"+(BuildConfig.DEBUG?".debug":""));
                sendBroadcast(intent);
                break;
            case R.id.tv_changeThread:
                transferWorkThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
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
                break;
            case R.id.tv_execute_task:
                // 小于核心线程数，直接创建新线程
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        final String msg = "pid = "+Process.myPid()+"; tid = "+Process.myTid();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
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

    private IMyService mService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // BinderProxy(IBinder) -> Proxy(IMyService)
            mService = IMyService.Stub.asInterface(service);
            Toast.makeText(MainActivity.this, "服务已连接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 服务所在进程被杀死
            mService = null;
            Toast.makeText(MainActivity.this, "服务已断开", Toast.LENGTH_SHORT).show();
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
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
        // Message一直持有Handler，直到Message.recycle
        Message message = Message.obtain(mHandler, runnable);
        message.what = 1;
        mHandler.sendMessageAtTime(message, SystemClock.uptimeMillis()+10*1000);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void dispatchMessage(Message msg) {
            // 1、Message.callback; 2、Handler.callback; 3、Handler.handleMessage
            super.dispatchMessage(msg);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    // 切换工作线程
    private void transferWorkThread(Runnable runnable) {
        executorService.execute(runnable);
    }

    ExecutorService executorService = new ThreadPoolExecutor(3, 5,
            1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128));
}
