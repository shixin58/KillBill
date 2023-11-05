package com.bride.ui_lib

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bride.baselib.PreferenceUtils
import java.util.Locale

open class BaseActivity : AppCompatActivity() {
    protected val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
    }

    private fun setLanguage() {
        val lang = PreferenceUtils.getString("language", Locale.ENGLISH.language)
        val resources = resources
        val config = resources.configuration
        if (lang == Locale.CHINESE.language) {
            config.setLocale(Locale.CHINESE)
        } else {
            config.setLocale(Locale.ENGLISH)
        }
        val dm = resources.displayMetrics
        resources.updateConfiguration(config, dm)
    }
}