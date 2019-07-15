package com.bride.client.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bride.baselib.BaseActivity;
import com.bride.client.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;

import dalvik.system.DexClassLoader;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>Created by shixin on 2019-05-21.
 */
public class ClassLoaderActivity extends BaseActivity {
    private static final String TAG = ClassLoaderActivity.class.getSimpleName();

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ClassLoaderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_loader);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_class_loader:
                // java.lang.BootClassLoader -> dalvik.system.PathClassLoader
                printClassLoaders(BinderActivity.class);

                // java.lang.BootClassLoader
                printClassLoaders(String.class);

                // java.lang.BootClassLoader
                printClassLoaders(TextView.class);
                break;
            case R.id.tv_download_plugin:
                OkHttpClient client = new OkHttpClient.Builder().build();
                Request request = new Request.Builder()
                        .url("http://47.91.249.201:8080/libs/app-arm-free-debug.zip")
                        .get()
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "onFailure", e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        File pluginDir = new File(getDir("plugin", Context.MODE_PRIVATE), "apk");
                        if (!pluginDir.exists()) {
                            pluginDir.mkdirs();
                        }
                        // /data/user/0/com.bride.client.debug/app_plugin/apk
                        Log.i(TAG, "onResponse "+pluginDir.getAbsolutePath());
                        BufferedOutputStream bos = new BufferedOutputStream(
                                new FileOutputStream(new File(pluginDir, "app-arm-free-debug.zip")));
                        bos.write(response.body().bytes());
                        bos.flush();
                        bos.close();
                    }
                });
                break;
            case R.id.tv_load_plugin:
                // 私有进程共享getDir目录，包名。
                String dexPath = new File(getDir("plugin", Context.MODE_PRIVATE), "apk/app-arm-free-debug.zip").getAbsolutePath();

                File dexOutputDir = new File(getDir("explugin", Context.MODE_PRIVATE), "com.roy.devil.debug");
                if (!dexOutputDir.exists())
                    dexOutputDir.mkdirs();
                String dexOutputPath = dexOutputDir.getAbsolutePath();
                // /data/user/0/com.bride.client.debug/app_explugin/com.roy.devil.debug
                Log.i(TAG, "path: "+dexPath+" "+dexOutputPath);

                String hostLibPath = getApplicationInfo().nativeLibraryDir;
                String pluginLibPath = dexOutputPath;
                String libPath = pluginLibPath + File.pathSeparator + hostLibPath;

                // 从指定目录zip文件提取dex。PathClassLoader仅能从安装过的apk加载
                DexClassLoader classLoader = new DexClassLoader(dexPath, dexOutputPath, libPath, ClassLoaderActivity.class.getClassLoader());
                try {
                    Class clazz = classLoader.loadClass("com.roy.devil.model.HomeModel");
                    // java.lang.BootClassLoader@665cd80 -> dalvik.system.PathClassLoader -> dalvik.system.DexClassLoader
                    printClassLoaders(clazz);
                    Constructor constructor = clazz.getConstructor(Class.class, String.class);
                    Object o = constructor.newInstance(int.class, "baby");
                    Toast.makeText(this, "showName = "+clazz.getField("showName").get(o), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public static void printClassLoaders(Class<?> clz) {
        StringBuilder sb = new StringBuilder();
        sb.append("load ").append(clz.getName()).append(": ");

        ClassLoader cl = clz.getClassLoader();
        while (cl != null) {
            sb.append(cl.toString()).append(", ");
            cl = cl.getParent();
        }
        Log.i(TAG, sb.toString());
    }
}
