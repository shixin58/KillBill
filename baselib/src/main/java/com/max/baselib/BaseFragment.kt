package com.max.baselib

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log

/**
 *
 * Created by shixin on 2018/9/4.
 */
open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("create", " ${this.javaClass.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("destroy", " ${this.javaClass.simpleName}")
    }
}
