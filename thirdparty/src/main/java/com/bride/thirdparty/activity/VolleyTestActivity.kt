package com.bride.thirdparty.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bride.baselib.BaseActivity
import com.bride.thirdparty.R
import com.bride.thirdparty.util.RxBus
import com.bride.thirdparty.Strategy.VolleyStrategy
import com.bride.thirdparty.ThirdPartyApplication
import com.bride.thirdparty.bean.MessageEvent
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
//        volleyStrategy.executeGetString()
        volleyStrategy.executePostString()
//        volleyStrategy.execute()
//        volleyStrategy.executeImage(findViewById(R.id.iv_demo))
//        volleyStrategy.executeImage2(findViewById(R.id.iv_demo1))
//        volleyStrategy.executeImage3(findViewById(R.id.iv_demo2))
    }

    override fun onStop() {
        super.onStop()
        ThirdPartyApplication.getInstance().requestQueue.cancelAll("xyz")
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.iv_demo -> {
                Toast.makeText(this, "post sticky 事件", Toast.LENGTH_SHORT).show()
                EventBus.getDefault().postSticky(MessageEvent("Cute"))
            }
            R.id.iv_demo1 -> {
                Toast.makeText(this, "RxBus.post事件", Toast.LENGTH_SHORT).show()
                RxBus.getInstance().post(MessageEvent("RxBus is awesome!"))
            }
            R.id.iv_demo2 -> {

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
