package com.bride.demon.module.framework.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.bride.ui_lib.BaseActivity
import com.bride.demon.R

class SingleTaskActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate $taskId ${hashCode()}")
        setContentView(R.layout.activity_single_task)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i(TAG, "onNewIntent $taskId ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy $taskId ${hashCode()}")
    }

    fun onClick(v: View?) {
        when {
            v?.id==R.id.tv_standard -> StandardActivity.openActivity(this)
            v?.id==R.id.tv_single_top -> SingleTopActivity.openActivity(this)
            v?.id==R.id.tv_single_task -> openActivity(this)
            v?.id==R.id.tv_single_instance -> SingleInstanceActivity.openActivity(this)
        }
    }

    companion object {
        private val TAG: String = SingleTaskActivity::class.java.simpleName

        fun openActivity(activity: FragmentActivity?) {
            val intent = Intent(activity, SingleTaskActivity::class.java)
            activity?.startActivity(intent)
        }
    }
}
