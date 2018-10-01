package com.victor.demon

import android.app.Application

import com.github.moduth.blockcanary.BlockCanary
import com.squareup.leakcanary.LeakCanary

/**
 *
 * Created by shixin on 2017/12/15.
 */
class DemoApplication : Application() {
    private val activityLifecycleCallbacks = MyActivityLifecycleCallbacks()

    override fun onCreate() {
        super.onCreate()

        initLeakCanary()

        initBlockCanary()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        // ArrayList, unregister/register, remove/add去重
        unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    private fun initBlockCanary() {
        // Do it on main process
        BlockCanary.install(this, AppBlockCanaryContext()).start()
    }
}
