package com.bride.demon

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.bride.baselib.*
import com.bride.demon.callback.MyActivityLifecycleCallbacks
import com.github.moduth.blockcanary.BlockCanary
import com.squareup.leakcanary.LeakCanary

/**
 *
 * Created by shixin on 2017/12/15.
 */
class DemonApplication : Application() {
    private val activityLifecycleCallbacks = MyActivityLifecycleCallbacks()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        CompatUtils.detectThread()
        CompatUtils.detectVm()
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        if (ResUtils.isMainThread(this)) {
            BlockCanary.install(this, AppBlockContext()).start()
        }

        ResUtils.setContext(this)
        PreferenceUtils.initialize(this, "demon_prefs")
        PermissionUtils.setContext(this)
        SystemStrategy.setContext(this)

        unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }
}
