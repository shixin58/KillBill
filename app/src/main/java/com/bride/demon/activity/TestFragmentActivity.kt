package com.bride.demon.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
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

        /**
         * 先import再引用类，不事先import直接包点类引用有差别吗
         * @param requestCode -1给startActivity用，>=0能被onActivityResult收到
         */
        fun openActivityForResult(activity: androidx.fragment.app.FragmentActivity, requestCode: Int) {
            val intent = Intent(activity, TestFragmentActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }

        fun openActivityForResult(fragment: androidx.fragment.app.Fragment, requestCode: Int) {
            val intent = Intent(fragment.activity, TestFragmentActivity::class.java)
            fragment.startActivityForResult(intent, requestCode)
        }
    }
}
