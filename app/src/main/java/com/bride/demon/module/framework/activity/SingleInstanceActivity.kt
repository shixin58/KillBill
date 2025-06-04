package com.bride.demon.module.framework.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.bride.ui_lib.BaseActivity
import com.bride.demon.R
import timber.log.Timber

class SingleInstanceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).i("onCreate $taskId ${hashCode()}")
        setContentView(R.layout.activity_single_instance)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Timber.tag(TAG).i("onNewIntent $taskId ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(TAG).i("onDestroy $taskId ${hashCode()}")
    }

    fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_standard -> StandardActivity.openActivity(this)
            R.id.tv_single_top -> SingleTopActivity.openActivity(this)
            R.id.tv_single_task -> SingleTaskActivity.openActivity(this)
            R.id.tv_single_instance -> openActivity(this)
        }
    }

    companion object {
        private val TAG: String = SingleInstanceActivity::class.java.simpleName

        fun openActivity(activity: FragmentActivity?) {
            val intent = Intent(activity, SingleInstanceActivity::class.java)
            activity?.startActivity(intent)
        }
    }
}
