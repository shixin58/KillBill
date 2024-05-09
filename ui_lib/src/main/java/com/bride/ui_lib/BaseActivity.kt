package com.bride.ui_lib

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bride.baselib.PreferenceUtils
import java.util.Locale

open class BaseActivity : AppCompatActivity() {
    protected val handler = Handler(Looper.getMainLooper())

    override fun attachBaseContext(newBase: Context) {
        val newContext = setLanguage(newBase)
        super.attachBaseContext(newContext)
    }

    private fun setLanguage(ctx: Context): Context {
        val lang = PreferenceUtils.getString("language", Locale.ENGLISH.language)
        val config = ctx.resources.configuration
        config.setLocale(if (lang == Locale.CHINESE.language) Locale.CHINESE else Locale.ENGLISH)
        return ctx.createConfigurationContext(config)
    }
}