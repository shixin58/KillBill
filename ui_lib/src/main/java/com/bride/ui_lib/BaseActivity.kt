package com.bride.ui_lib

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bride.baselib.Constants
import com.bride.baselib.PreferenceUtils

open class BaseActivity : AppCompatActivity() {
    protected val handler = Handler(Looper.getMainLooper())

    var mLangInt: Int = Constants.LANG_ENGLISH

    override fun attachBaseContext(newBase: Context) {
        val newContext = setLanguage(newBase)
        super.attachBaseContext(newContext)
    }

    private fun setLanguage(ctx: Context): Context {
        val langInt = PreferenceUtils.getInt(Constants.KEY_LANG, Constants.LANG_ENGLISH)
        val config = ctx.resources.configuration
        config.setLocale(Constants.getLocale(langInt))
        mLangInt = langInt
        return ctx.createConfigurationContext(config)
    }

    override fun onStart() {
        super.onStart()
        if (mLangInt != PreferenceUtils.getInt(Constants.KEY_LANG, Constants.LANG_ENGLISH)) {
            recreate()
        }
    }
}