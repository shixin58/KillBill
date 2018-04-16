package com.yiche.example.newestversiondemo.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yiche.example.newestversiondemo.R
import com.yiche.example.newestversiondemo.activity.RecyclerViewActivity
import com.yiche.example.newestversiondemo.activity.TestPlatformActivity

class HomeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id==R.id.button_recycler_view) {
            RecyclerViewActivity.openActivity(activity)
        }else if (v?.id==R.id.button_fragment) {
            TestPlatformActivity.openActivityForResult(this, 1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.findViewById<View>(R.id.button_recycler_view)?.setOnClickListener(this)
        activity?.findViewById<View>(R.id.button_fragment)?.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 接收返回
        Log.i("Victor", "requestCode-$requestCode")
    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
