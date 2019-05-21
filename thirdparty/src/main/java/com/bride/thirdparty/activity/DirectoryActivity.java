package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.R;

import java.io.File;

/**
 * <p>Created by shixin on 2019/4/3.
 */
public class DirectoryActivity extends BaseActivity {
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, DirectoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            // 内部存储，非root不可见，卸载app不删除
            // /system, /data, /cache
            case R.id.tv_dir1:
                Toast.makeText(this, Environment.getRootDirectory().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir2:
                Toast.makeText(this, Environment.getDataDirectory().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir3:
                Toast.makeText(this, Environment.getDownloadCacheDirectory().getPath(), Toast.LENGTH_SHORT).show();
                break;
            // 外部存储，可见，卸载app不删除
            // /storage/emulated/0, /storage/emulated/0/Music
            case R.id.tv_dir4:
                Toast.makeText(this, Environment.getExternalStorageDirectory().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir5:
                Toast.makeText(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath(), Toast.LENGTH_SHORT).show();
                break;
            // 外部存储，可见，卸载app删除
            // /storage/emulated/0/Android/data/package/cache(clear cache), /storage/emulated/0/Android/data/package/files/Music
            case R.id.tv_dir6:
                Toast.makeText(this, getExternalCacheDir().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir7:
                Toast.makeText(this, getExternalFilesDir(Environment.DIRECTORY_MUSIC).getPath(), Toast.LENGTH_SHORT).show();
                break;
            // 内部存储，非root不可见，卸载app删除
            // /data/data/package/cache(clear cache), /data/data/package/files, /data/data/package/databases, /data/data/package/shared_prefs
            case R.id.tv_dir8:
                Toast.makeText(this, getCacheDir().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir9:
                Toast.makeText(this, getFilesDir().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir10:
                File pluginDir = new File(getDir("plugin", Context.MODE_PRIVATE), "apk");
                if (!pluginDir.exists()) {
                    pluginDir.mkdirs();
                }
                Toast.makeText(this, pluginDir.getPath(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
