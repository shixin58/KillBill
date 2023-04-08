package com.bride.thirdparty.activity

import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.bride.baselib.SystemStrategy
import com.bride.baselib.toast
import com.bride.thirdparty.databinding.ActivityDirectoryBinding
import com.bride.ui_lib.BaseActivity
import java.io.File

class DirectoryActivity : BaseActivity() {
    private lateinit var mBinding: ActivityDirectoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDirectoryBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    fun onClick(v: View) {
        when(v) {
            // 内部存储(仅root可见)，卸载app不删除。
            // /system,
            // /data,
            // /data/cache
            mBinding.tvRootDir -> toast(Environment.getRootDirectory().path)
            mBinding.tvDataDir -> toast(Environment.getDataDirectory().path)
            mBinding.tvDownloadCacheDir -> toast(Environment.getDownloadCacheDirectory().path)
            // 外部存储(可见)，卸载app不删除。
            // /storage/emulated/0,
            // /storage/emulated/0/Music
            mBinding.tvExternalStorageDir -> toast(Environment.getExternalStorageDirectory().path)
            mBinding.tvExternalStoragePublicDir -> toast(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).path)
            // 外部存储(可见)，卸载app删除。常用
            // /storage/emulated/0/Android/data/$package/cache(clear cache),
            // /storage/emulated/0/Android/data/$package/files/Music
            mBinding.tvExternalCacheDir -> toast(externalCacheDir?.path)
            mBinding.tvExternalFilesDir -> toast(getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.path)
            // 内部存储(仅root可见)，卸载app删除。常用
            // /data/user/0/$package,
            // /data/user/0/$package/cache,
            // /data/user/0/$package/files,
            // /data/user/0/$package/databases,
            // /data/user/0/$package/shared_prefs
            mBinding.tvAppDataDir -> {
                if (VERSION.SDK_INT >= VERSION_CODES.N) {
                    toast(dataDir.path)
                }
            }
            mBinding.tvCacheDir -> toast(cacheDir.path)
            mBinding.tvFilesDir -> toast(filesDir.path)
            mBinding.tvAppDir -> {
                val pluginDir = File(getDir("plugin", Context.MODE_PRIVATE), "apk")
                if (!pluginDir.exists()) {
                    pluginDir.mkdirs()
                }
                toast(pluginDir.path)
            }
            mBinding.tvAndroidId -> toast(SystemStrategy.getAndroidId())
        }
    }
}