package com.bride.demon.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.bride.demon.R
import com.bride.demon.activity.*
import com.bride.demon.module.rong.activity.RongDemoActivity
import com.bride.ui_lib.BaseFragment

class HomeFragment : BaseFragment(), View.OnClickListener {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.button_recycler_view -> RecyclerViewActivity.openActivity(activity)
            R.id.button_touch -> TouchActivity.openActivity(activity, 0)
            R.id.button_nested_list -> NestedListActivity.openActivity(activity)
            R.id.button_upload -> UploadActivity.openActivity(activity)
            R.id.button_webp -> WebpActivity.openActivity(activity)
//            R.id.button_glide -> GlideActivity.openActivity(activity)
            R.id.button_glide -> {
                ARouter.getInstance().build("/demon/activity").navigation()
            }
            R.id.button_relative_layout -> RelativeLayoutActivity.openActivity(activity)
            R.id.button_volley -> VolleyActivity.openActivity(activity)
            R.id.button_rong -> RongDemoActivity.openActivity(activity)
            R.id.button_exception -> ExceptionActivity.openActivity(activity)
            R.id.button_serializable -> SerializableActivity.openActivity(activity)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
