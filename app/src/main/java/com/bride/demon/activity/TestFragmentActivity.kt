package com.bride.demon.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import com.bride.baselib.BaseActivity
import com.bride.demon.R

class TestFragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_platform)
    }

    companion object {

        fun openActivity(context: Context) {
            val intent = Intent(context, TestFragmentActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }

        fun openActivityForResult(activity: FragmentActivity, requestCode: Int) {
            val intent = Intent(activity, TestFragmentActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }

        fun openActivityForResult(fragment: Fragment, requestCode: Int) {
            val intent = Intent(fragment.activity, TestFragmentActivity::class.java)
            fragment.startActivityForResult(intent, requestCode)
        }
    }
}
