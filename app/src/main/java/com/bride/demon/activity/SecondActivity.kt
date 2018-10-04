package com.bride.demon.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import com.bride.baselib.BaseActivity
import com.bride.demon.R

/**
 *
 * Created by shixin on 2018/9/6.
 */
class SecondActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when {
            v?.id==R.id.tv_jump -> ThirdActivity.openActivity(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        initView()
    }

    private fun initView() {
        findViewById<View>(R.id.tv_jump)?.setOnClickListener(this)
    }

    companion object {
        fun openActivity(activity: FragmentActivity?) {
            val intent = Intent(activity, SecondActivity::class.java)
            activity?.startActivity(intent)
        }
    }
}
