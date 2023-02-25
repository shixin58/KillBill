package com.bride.widget

import android.app.Application
import android.content.Context
import com.bride.baselib.CompatUtils
import com.bride.baselib.PreferenceUtils
import com.bride.baselib.ResUtils
import com.bride.baselib.appContext
import timber.log.Timber

/**
 *
 * Created by shixin on 2018/10/30.
 */
class WidgetApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        CompatUtils.detectThread()
        CompatUtils.detectVm()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        ResUtils.setContext(this)
        PreferenceUtils.initialize(this, "widget_prefs")

        Timber.plant(Timber.DebugTree())
    }
}