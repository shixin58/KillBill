package com.bride.demon.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.bride.ui_lib.BaseActivity
import com.bride.ui_lib.BaseFragment
import com.bride.demon.R

/**
 * test startActivityForResult, View#postInvalidate, Timer
 */
class PlatformActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_platform)
        Log.i(TAG, "onCreate $taskId ${hashCode()}")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i(TAG, "onNewIntent $taskId ${hashCode()}")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy $taskId ${hashCode()}")
    }

    companion object {

        private val TAG: String = PlatformActivity::class.java.simpleName

        fun openActivity(context: Context) {
            val intent = Intent(context, PlatformActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }

        /**
         * 先import再引用类，不事先import直接包点类引用有差别吗
         * @param requestCode -1给startActivity用，>=0能被onActivityResult收到
         */
        fun openActivityForResult(activity: BaseActivity, requestCode: Int) {
            val intent = Intent(activity, PlatformActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }

        fun openActivityForResult(fragment: BaseFragment, requestCode: Int) {
            val intent = Intent(fragment.activity, PlatformActivity::class.java)
            fragment.startActivityForResult(intent, requestCode)
        }
    }
}
