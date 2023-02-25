package com.bride.demon

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.bride.baselib.*
import com.bride.baselib.net.VolleyWrapper
import com.bride.baselib.CustomActivityLifecycleCallbacks
import com.github.moduth.blockcanary.BlockCanary
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 在ContextWrapper#attachBaseContext()设置UncaughtExceptionHandler
 * Created by shixin on 2017/12/15.
 */
class DemonApplication : Application() {
    companion object {
        lateinit var instance: DemonApplication
            private set
        val exec: ExecutorService by lazy { Executors.newCachedThreadPool() }
    }

    private val activityLifecycleCallbacks = CustomActivityLifecycleCallbacks()
    private val executorService: ExecutorService = Executors.newFixedThreadPool(8)

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        CompatUtils.detectThread()
        CompatUtils.detectVm()
        GlobalThreadUncaughtExceptionHandler.setup()
    }

    override fun onCreate() {
        super.onCreate()
        if (ResUtils.isMainThread(this)) {
            BlockCanary.install(this, AppBlockContext()).start()
        }

        instance = this
        appContext = this
        ResUtils.setContext(this)
        PreferenceUtils.initialize(this, "demon_prefs")
        PermissionUtils.setContext(this)
        SystemStrategy.setContext(this)
        VolleyWrapper.init(this)

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)

        initRouter()
        Timber.plant(Timber.DebugTree())
    }

    fun getExecutor(): Executor {
        return executorService
    }

    private fun initRouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}
