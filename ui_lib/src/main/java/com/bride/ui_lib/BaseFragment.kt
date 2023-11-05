package com.bride.ui_lib

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import timber.log.Timber

open class BaseFragment : Fragment() {
    companion object {
        @JvmStatic
        protected lateinit var TAG: String
    }

    protected val handler = Handler(Looper.getMainLooper())

    init {
        TAG = javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(TAG).d("onDestroy")
    }
}