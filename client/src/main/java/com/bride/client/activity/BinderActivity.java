package com.bride.client.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bride.client.databinding.ActivityBinderBinding;
import com.bride.ui_lib.BaseActivity;
import com.bride.client.BuildConfig;
import com.bride.demon.Form;
import com.bride.demon.IMyService;

/**
 * <p>Created by shixin on 2019-05-15.
 */
public class BinderActivity extends BaseActivity {
    private ActivityBinderBinding mBinding;

    private IMyService mService;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, BinderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBinderBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }

    public void onClick(View v) {
        Intent intent;
        if (v == mBinding.tvSwitch) {
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
                // IllegalArgumentException: Service not registered: com.bride.client.MainActivity$3@dc78e3b
                unbindService(mServiceConnection);
                mService = null;
            }
        } else if (v == mBinding.tvUseService) {
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
        }
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // BinderProxy(IBinder) -> Proxy(IMyService)
            mService = IMyService.Stub.asInterface(service);
            Toast.makeText(BinderActivity.this, "服务已连接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 服务所在进程被杀死
            mService = null;
            Toast.makeText(BinderActivity.this, "服务已断开", Toast.LENGTH_SHORT).show();
        }
    };
}
