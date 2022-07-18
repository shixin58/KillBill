package com.bride.client.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bride.client.databinding.ActivityClassLoaderBinding;
import com.bride.ui_lib.BaseActivity;

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
 * Java类加载器：通过类的全限定名获取类的二进制字节流。
 * <p>双亲委托模型：特定类加载器接到加载类请求，先将加载任务委托给父加载器，依次递归。如果父类加载器能完成加载任务，成功返回；只有父类加载器无法完成加载任务，才自己去加载。
 * <p>Created by shixin on 2019-05-21.
 */
public class ClassLoaderActivity extends BaseActivity {
    private static final String TAG = ClassLoaderActivity.class.getSimpleName();

    private ActivityClassLoaderBinding mBinding;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ClassLoaderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityClassLoaderBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.setActivity(this);
    }

    public void onClick(View v) {
        if (v==mBinding.tvClassLoader) {
            // dalvik.system.PathClassLoader, java.lang.BootClassLoader
            printClassLoaders(ClassLoaderActivity.class);

            // java.lang.BootClassLoader
            printClassLoaders(String.class);

            // java.lang.BootClassLoader
            printClassLoaders(TextView.class);
        } else if (v==mBinding.tvDownloadPlugin) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();
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
        } else if (v==mBinding.tvLoadPlugin) {
            // 私有进程共享getDir目录、包名。
            final String dexPath = new File(getDir("plugin", Context.MODE_PRIVATE), "apk/app-arm-free-debug.zip").getAbsolutePath();

            final File dexOutputDir = new File(getDir("explugin", Context.MODE_PRIVATE), "com.roy.devil.debug");
            if (!dexOutputDir.exists())
                dexOutputDir.mkdirs();
            // /data/user/0/com.bride.client.debug/app_explugin/com.roy.devil.debug
            String dexOutputPath = dexOutputDir.getAbsolutePath();

            // 构造librarySearchPath
            final String pluginLibPath = dexOutputPath;
            final String hostLibPath = getApplicationInfo().nativeLibraryDir;
            final String librarySearchPath = pluginLibPath + File.pathSeparator + hostLibPath;

            // 从指定目录zip文件提取dex。PathClassLoader仅能从安装过的apk加载
            DexClassLoader classLoader = new DexClassLoader(dexPath, dexOutputPath, librarySearchPath, ClassLoaderActivity.class.getClassLoader());
            try {
                Class clazz = classLoader.loadClass("com.roy.devil.model.HomeModel");
                // dalvik.system.DexClassLoader, dalvik.system.PathClassLoader, java.lang.BootClassLoader
                printClassLoaders(clazz);
                Constructor constructor = clazz.getConstructor(Class.class, String.class);
                Object o = constructor.newInstance(int.class, "baby");
                Toast.makeText(this, "showName = "+clazz.getField("showName").get(o), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void printClassLoaders(Class<?> clz) {
        StringBuilder sb = new StringBuilder();
        sb.append("load ").append(clz.getName()).append(": \n");

        ClassLoader cl = clz.getClassLoader();
        while (cl != null) {
            sb.append(cl).append(", ");
            cl = cl.getParent();
        }
        Log.i(TAG, sb.toString());
    }
}
