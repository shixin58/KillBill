package com.bride.demon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bride.demon.module.kotlin_practice.KotlinPracticeActivity
import com.bride.ui_lib.BaseFragment
import com.bride.demon.databinding.FragmentDashboardBinding
import com.bride.demon.module.framework.activity.SingleInstanceActivity
import com.bride.demon.module.framework.activity.SingleTaskActivity
import com.bride.demon.module.framework.activity.SingleTopActivity
import com.bride.demon.module.framework.activity.StandardActivity

/**
 * Activity launch mode
 * Created by shixin on 2018/4/15.
 */
class DashboardFragment : BaseFragment() {
    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }

    private lateinit var mBinding: FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.fragment = this
    }

    fun onClick(v: View) {
        when(v) {
            mBinding.tvStandard -> StandardActivity.openActivity(activity)
            mBinding.tvSingleTop -> SingleTopActivity.openActivity(activity)
            mBinding.tvSingleTask -> SingleTaskActivity.openActivity(activity)
            mBinding.tvSingleInstance -> SingleInstanceActivity.openActivity(activity)
            mBinding.tvKotlinPractice -> KotlinPracticeActivity.openActivity(requireActivity())
        }
    }
}
