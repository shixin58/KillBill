package com.bride.client

import android.app.Application
import com.bride.baselib.*
import timber.log.Timber

/**
 *
 * Created by shixin on 2018/10/30.
 */
class ClientApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        ResUtils.setContext(this)
        PreferenceUtils.initialize(this, "client_prefs")
        PermissionUtils.setContext(this)
        SystemStrategy.setContext(this)

        Timber.plant(Timber.DebugTree())
    }
}