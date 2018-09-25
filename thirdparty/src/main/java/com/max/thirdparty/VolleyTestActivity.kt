package com.max.thirdparty

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView

import com.android.volley.toolbox.NetworkImageView
import com.max.baselib.BaseActivity
import com.max.thirdparty.Strategy.VolleyStrategy
import com.max.thirdparty.bean.MessageEvent

import org.greenrobot.eventbus.EventBus

/**
 *
 * Created by shixin on 2018/9/21.
 */
class VolleyTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volley_test)
        initView()
    }

    private fun initView() {
        val volleyStrategy = VolleyStrategy()
        volleyStrategy.execute()
        volleyStrategy.executeImage(findViewById<View>(R.id.iv_demo) as ImageView)
        volleyStrategy.executeImage3(findViewById<View>(R.id.iv_demo2) as NetworkImageView)
    }

    override fun onStop() {
        super.onStop()
        MyApplication.getInstance().requestQueue.cancelAll("xyz")
    }

    fun onClick(view: View) {
        EventBus.getDefault().postSticky(MessageEvent())
    }

    companion object {
        fun openActivity(context: Context) {
            val intent = Intent(context, VolleyTestActivity::class.java)
            context.startActivity(intent)
        }
    }
}
