package com.bride.thirdparty.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bride.baselib.BaseActivity
import com.bride.thirdparty.R
import com.bride.thirdparty.Strategy.VolleyStrategy
import com.bride.thirdparty.ThirdPartyApplication
import com.bride.thirdparty.bean.MessageEvent
import com.bride.thirdparty.util.RxBus
import org.greenrobot.eventbus.EventBus

/**
 *
 * Created by shixin on 2018/9/21.
 */
class VolleyTestActivity : BaseActivity() {

    private val volleyStrategy = VolleyStrategy()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volley_test)
        initView()
    }

    private fun initView() {
    }

    override fun onStop() {
        super.onStop()
        ThirdPartyApplication.getInstance().requestQueue.cancelAll("xyz")
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.tv_get_string -> {
                volleyStrategy.executeGetString()
            }
            R.id.tv_post_string -> {
                volleyStrategy.executePostString()
            }
            R.id.tv_post_json -> {
                volleyStrategy.execute()
            }
            R.id.image -> {
                volleyStrategy.executeImage(findViewById(R.id.iv_demo))
            }
            R.id.image1 -> {
                volleyStrategy.executeImage2(findViewById(R.id.iv_demo1))
            }
            R.id.image2 -> {
                volleyStrategy.executeImage3(findViewById(R.id.iv_demo2))
            }
            R.id.tv_post_sticky -> {
                EventBus.getDefault().postSticky(MessageEvent("Cute"))
            }
            R.id.tv_rxbus_post -> {
                RxBus.getInstance().post(MessageEvent("RxBus is awesome!"))
            }
        }
    }

    companion object {
        fun openActivity(context: Context) {
            val intent = Intent(context, VolleyTestActivity::class.java)
            context.startActivity(intent)
        }
    }
}
