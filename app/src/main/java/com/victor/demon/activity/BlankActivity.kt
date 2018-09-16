package com.victor.demon.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.max.baselib.BaseActivity
import com.victor.demon.R

/**
 *
 * Created by shixin on 2018/4/26.
 */
class BlankActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blank)
        Log.i("lifecycleB", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i("lifecycleB", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("lifecycleB", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("lifecycleB", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("lifecycleB", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("lifecycleB", "onDestroy")
    }

    companion object {

        fun openActivity(context: FragmentActivity?) {
            val intent = Intent(context, BlankActivity::class.java)
            context?.startActivity(intent)
        }
    }
}