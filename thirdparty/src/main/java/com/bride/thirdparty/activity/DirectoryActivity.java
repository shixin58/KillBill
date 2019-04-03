package com.bride.thirdparty.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.bride.baselib.BaseActivity;
import com.bride.thirdparty.R;

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
            case R.id.tv_dir1:
                Toast.makeText(this, Environment.getRootDirectory().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir2:
                Toast.makeText(this, Environment.getDataDirectory().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir3:
                Toast.makeText(this, Environment.getDownloadCacheDirectory().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir4:
                Toast.makeText(this, Environment.getExternalStorageDirectory().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir5:
                Toast.makeText(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir6:
                Toast.makeText(this, getExternalCacheDir().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir7:
                Toast.makeText(this, getExternalFilesDir(Environment.DIRECTORY_MUSIC).getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir8:
                Toast.makeText(this, getCacheDir().getPath(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_dir9:
                Toast.makeText(this, getFilesDir().getPath(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
