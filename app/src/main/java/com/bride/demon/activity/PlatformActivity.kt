package com.bride.demon.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bride.ui_lib.BaseActivity
import com.bride.ui_lib.BaseFragment
import com.bride.demon.R
import timber.log.Timber

/**
 * test startActivityForResult, View#postInvalidate, Timer
 */
class PlatformActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_platform)
        Timber.tag(TAG).i("onCreate $taskId ${hashCode()}")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Timber.tag(TAG).i("onNewIntent $taskId ${hashCode()}")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.tag(TAG).i("onRestart")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).i("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).i("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(TAG).i("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(TAG).i("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(TAG).i("onDestroy $taskId ${hashCode()}")
    }

    companion object {

        private val TAG: String = PlatformActivity::class.java.simpleName

        fun openActivity(context: Context) {
            val intent = Intent(context, PlatformActivity::class.java)
            context.startActivity(intent)
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
