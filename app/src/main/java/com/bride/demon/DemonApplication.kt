package com.bride.demon

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.bride.baselib.*
import com.bride.baselib.net.VolleyWrapper
import com.bride.baselib.CustomActivityLifecycleCallbacks
import com.github.moduth.blockcanary.BlockCanary
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 在ContextWrapper#attachBaseContext()设置UncaughtExceptionHandler
 * Created by shixin on 2017/12/15.
 */
class DemonApplication : Application() {
    private val activityLifecycleCallbacks = CustomActivityLifecycleCallbacks()
    private val executorService: ExecutorService = Executors.newFixedThreadPool(8)

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        CompatUtils.detectThread()
        CompatUtils.detectVm()
        Thread.setDefaultUncaughtExceptionHandler(CustomUncaughtExceptionHandler())
    }

    override fun onCreate() {
        super.onCreate()
        if (ResUtils.isMainThread(this)) {
            BlockCanary.install(this, AppBlockContext()).start()
        }

        instance = this
        ResUtils.setContext(this)
        PreferenceUtils.initialize(this, "demon_prefs")
        PermissionUtils.setContext(this)
        SystemStrategy.setContext(this)
        VolleyWrapper.init(this)

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        ProcessLifecycleOwner.get().lifecycle.addObserver(ProcessLifecycleObserver)

        initRouter()
    }

    fun getExecutor(): Executor {
        return executorService
    }

    companion object {
        private lateinit var instance: DemonApplication
        val exec: ExecutorService by lazy { Executors.newCachedThreadPool() }

        fun getInstance(): DemonApplication {
            return instance
        }
    }

    private fun initRouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    class CustomUncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(t: Thread, e: Throwable) {
            Log.e("UncaughtException", "${t.name} uncaughtException()", e)
        }
    }
}
