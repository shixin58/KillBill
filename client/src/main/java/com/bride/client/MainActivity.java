package com.bride.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bride.baselib.ActivityComponentName;
import com.bride.baselib.ActivitySchemas;
import com.bride.baselib.BaseActivity;
import com.bride.demon.Form;
import com.bride.demon.IMyService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Created by shixin on 2018/9/4.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_action_view).setOnClickListener(this);
        findViewById(R.id.tv_implicit).setOnClickListener(this);
        findViewById(R.id.tv_app).setOnClickListener(this);
        findViewById(R.id.tv_switch).setOnClickListener(this);
        findViewById(R.id.tv_use_service).setOnClickListener(this);
        findViewById(R.id.tv_changeThread).setOnClickListener(this);
        findViewById(R.id.tv_execute_task).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_action_view:
                // 1、app进程间调用页面，配置scheme
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(ActivitySchemas.RECYCLER_VIEW_SCHEMA));
                String urlString = new ActivitySchemas(ActivitySchemas.TEST_TOUCH_SCHEMA).setParam("type", 0).getUriString();
                intent.setData(Uri.parse(urlString));
                // 调用系统浏览器
//                intent.setData(Uri.parse("https://www.sogou.com"));
                startActivity(intent);
                break;
            case R.id.tv_implicit:
                // 2、自定义ACTION，隐式启动
                // 若俩页面ACTION相同，弹选择对话框
                /*Intent intent3 = new Intent();
                intent3.setAction("com.bride.demon.activity.TestFragmentActivity");
                startActivity(intent3);*/
                intent = new Intent();
                intent.setComponent(ActivityComponentName.TEST_FRAGMENT);
                startActivity(intent);
                break;
            case R.id.tv_app:
                // app进程间调用页面，配置包名和类名
                Intent intent2 = new Intent();
//                intent2.setClassName("com.bride.demon", "com.bride.demon.MainActivity")
                ComponentName componentName = new ComponentName("com.bride.demon", "com.bride.demon.MainActivity");
                intent2.setComponent(componentName);
                intent2.setAction(Intent.ACTION_MAIN);
                // 多个category放在ArraySet里
                intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(intent2);
                break;
            case R.id.tv_switch:
                if(mService==null) {
                    // 启动服务并拿到服务代理
                    Intent intent1 = new Intent("com.bride.demon.IMyService");
                    intent1.setPackage("com.bride.demon");
                    bindService(intent1, mServiceConnection, Context.BIND_AUTO_CREATE);
                }else {
                    unbindService(mServiceConnection);
                }
                break;
            case R.id.tv_use_service:
                if(mService!=null) {
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
            case R.id.tv_changeThread:
                // 线程切换
                transferWorkThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                            // 持有Activity句柄
//                            MainActivity.this.runOnUiThread(mRunnable);
                            transferMainThread(mRunnable);
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
                        Log.i("execute", "run");
                    }
                });
                break;
        }
    }

    private IMyService mService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IMyService.Stub.asInterface(service);
            Toast.makeText(MainActivity.this, "服务已连接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            Toast.makeText(MainActivity.this, "服务已断开", Toast.LENGTH_SHORT).show();
        }
    };

    // 切换主线程
    private void transferMainThread(Runnable runnable) {
//        mHandler.post(runnable);
        // Message一直持有Handler，直到Message.recycle
        Message message = Message.obtain(mHandler, runnable);
        message.what = 1;
        mHandler.sendMessageAtTime(message, SystemClock.uptimeMillis()+10*1000);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        }
    };

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // 更新UI
            Log.i("run", "更新UI");
        }
    };

    // 切换工作线程
    private void transferWorkThread(Runnable runnable) {
        executorService.execute(runnable);
    }

    ExecutorService executorService = new ThreadPoolExecutor(3, 5,
            1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128));
}
