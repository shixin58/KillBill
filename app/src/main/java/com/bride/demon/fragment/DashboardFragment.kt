package com.bride.demon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bride.baselib.BaseFragment
import com.bride.demon.R
import com.bride.demon.activity.SingleInstanceActivity
import com.bride.demon.activity.SingleTaskActivity
import com.bride.demon.activity.SingleTopActivity
import com.bride.demon.activity.StandardActivity

/**
 * Activity launch mode
 * Created by shixin on 2018/4/15.
 */
class DashboardFragment : BaseFragment() {

    fun onClick(v: View?) {
        when {
            v?.id==R.id.tv_standard -> StandardActivity.openActivity(activity)
            v?.id==R.id.tv_single_top -> SingleTopActivity.openActivity(activity)
            v?.id==R.id.tv_single_task -> SingleTaskActivity.openActivity(activity)
            v?.id==R.id.tv_single_instance -> SingleInstanceActivity.openActivity(activity)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}
