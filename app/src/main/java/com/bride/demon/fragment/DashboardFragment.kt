package com.bride.demon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bride.baselib.BaseFragment
import com.bride.demon.R
import com.bride.demon.activity.SecondActivity

/**
 *
 * Created by shixin on 2018/4/15.
 */
class DashboardFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when {
            // 测试Activity启动方式
            v?.id==R.id.tv_jump -> SecondActivity.openActivity(activity)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<View>(R.id.tv_jump)?.setOnClickListener(this)
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}
