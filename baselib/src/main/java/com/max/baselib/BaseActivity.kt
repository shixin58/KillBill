package com.max.baselib

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 *
 * Created by shixin on 2018/4/26.
 */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("create", "${this.javaClass.simpleName}; taskId=$taskId $isTaskRoot")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i("onNewIntent", "${this.javaClass.simpleName}; taskId=$taskId $isTaskRoot")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("destroy", " " + this.javaClass.simpleName)
    }
}
