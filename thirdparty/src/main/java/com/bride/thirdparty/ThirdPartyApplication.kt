package com.bride.thirdparty

import android.app.Application
import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.Looper
import com.bride.baselib.*
import com.bride.thirdparty.util.ObjectBox
import com.facebook.stetho.Stetho
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.lang.Thread.UncaughtExceptionHandler

/**
 *
 * Created by shixin on 2018/9/20.
 */
class ThirdPartyApplication : Application() {
    companion object {
        lateinit var instance: ThirdPartyApplication
            private set
    }

    val handler = Handler(Looper.getMainLooper())

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        Thread.setDefaultUncaughtExceptionHandler(MyUncaughtExceptionHandler())
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = this
        ObjectBox.init(this)
        ResUtils.setContext(this)
        PreferenceUtils.initialize(this, "thirdparty_prefs")
        PermissionUtils.setContext(this)
        SystemStrategy.setContext(this)

        Stetho.initializeWithDefaults(this)
        Timber.plant(Timber.DebugTree())
    }

    // 可以保存至文件，也可上传至服务器
    private class MyUncaughtExceptionHandler : UncaughtExceptionHandler {
        override fun uncaughtException(t: Thread, e: Throwable) {
            try {
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "exceptions.txt")
                if (!file.exists() && !file.createNewFile()) return
                val pw = PrintWriter(file)
                pw.println(t.name)
                e.printStackTrace(pw)
                pw.flush()
                pw.close()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
        }
    }
}