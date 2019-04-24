package com.bride.demon.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.bride.baselib.BaseActivity
import com.bride.demon.R

/**
 *
 * Created by shixin on 2018/9/6.
 */
class SingleInstanceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate $taskId")
        setContentView(R.layout.activity_single_instance)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i(TAG, "onNewIntent $taskId")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy $taskId")
    }

    fun onClick(v: View?) {
        when {
            v?.id==R.id.tv_standard -> StandardActivity.openActivity(this)
            v?.id==R.id.tv_single_top -> SingleTopActivity.openActivity(this)
            v?.id==R.id.tv_single_task -> SingleTaskActivity.openActivity(this)
            v?.id==R.id.tv_single_instance -> SingleInstanceActivity.openActivity(this)
        }
    }

    companion object {
        fun openActivity(activity: FragmentActivity?) {
            val intent = Intent(activity, SingleInstanceActivity::class.java)
            activity?.startActivity(intent)
        }
    }
}
