package com.bride.thirdparty;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.bride.baselib.CompatUtils;
import com.bride.baselib.PermissionUtils;
import com.bride.baselib.PreferenceUtils;
import com.bride.baselib.ResUtils;
import com.bride.baselib.SystemStrategy;
import com.facebook.stetho.Stetho;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>Created by shixin on 2018/9/20.
 */
public class ThirdPartyApplication extends Application {
    private static ThirdPartyApplication application;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static ThirdPartyApplication getInstance() {
        return application;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        CompatUtils.detectThread();
        CompatUtils.detectVm();
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        application = this;

        ResUtils.setContext(this);
        PreferenceUtils.initialize(this, "thirdparty_prefs");
        PermissionUtils.setContext(this);
        SystemStrategy.setContext(this);
    }

    public Handler getHandler() {
        return mHandler;
    }

    // 可以保存至文件，也可上传至服务器
    static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+"exceptions.txt");
                if (!file.exists() && !file.createNewFile())
                    return;
                PrintWriter pw = new PrintWriter(file);
                pw.println(t.getName());
                e.printStackTrace(pw);
                pw.flush();
                pw.close();
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
