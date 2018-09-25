package com.victor.demon.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.max.baselib.BaseFragment
import com.victor.demon.R
import com.victor.demon.activity.BlankActivity
import com.victor.demon.activity.RecyclerView2Activity
import com.victor.demon.activity.RecyclerViewActivity
import com.victor.demon.activity.TestFragmentActivity

class HomeFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when {
            v?.id== R.id.button_recycler_view -> RecyclerViewActivity.openActivity(activity)
            v?.id==R.id.button_fragment -> TestFragmentActivity.openActivityForResult(this, 1)
            v?.id==R.id.button_base -> BlankActivity.openActivity(activity)
            v?.id==R.id.button_recycler_view2 -> RecyclerView2Activity.openActivity(activity)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<View>(R.id.button_recycler_view)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_fragment)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_base)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_recycler_view2)?.setOnClickListener(this)
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
