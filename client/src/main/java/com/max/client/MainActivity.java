package com.max.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.max.baselib.BaseActivity;
import com.victor.demon.Form;
import com.victor.demon.IMyService;

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
        findViewById(R.id.tv_title).setOnClickListener(this);
        findViewById(R.id.tv_app).setOnClickListener(this);
        findViewById(R.id.tv_switch).setOnClickListener(this);
        findViewById(R.id.tv_use_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title:
                // app进程间调用页面
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("max://demon/recycler.view"));
                startActivity(intent);
                break;
            case R.id.tv_app:
                Intent intent2 = new Intent();
//                intent2.setClassName("com.victor.demon", "com.victor.demon.MainActivity")
                ComponentName componentName = new ComponentName("com.victor.demon", "com.victor.demon.MainActivity");
                intent2.setComponent(componentName);
                intent2.setAction(Intent.ACTION_MAIN);
                // 多个category放在ArraySet里
                intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(intent2);
                break;
            case R.id.tv_switch:
                if(mService==null) {
                    // 启动服务并拿到服务代理
                    Intent intent1 = new Intent("com.victor.demon.IMyService");
                    intent1.setPackage("com.victor.demon");
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
}
