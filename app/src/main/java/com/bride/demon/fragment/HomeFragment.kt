package com.bride.demon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.bride.demon.R
import com.bride.demon.activity.*
import com.bride.demon.databinding.FragmentHomeBinding
import com.bride.demon.model.DailyJob
import com.bride.ui_lib.BaseFragment

class HomeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mViewDataBinding: FragmentHomeBinding

    private val dailyJob = DailyJob().also {
        it.name = "Work"
        it.priority = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mViewDataBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.fragment = this
        mViewDataBinding.dailyJob = dailyJob
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onClick(v: View?) {
        val ac = activity?:return
        when (v?.id){
            R.id.button_recycler_view -> RecyclerViewActivity.openActivity(ac)
            R.id.button_touch -> TouchActivity.openActivity(ac, 0)
            R.id.button_nested_list -> NestedListActivity.openActivity(ac)
            R.id.button_upload -> UploadActivity.openActivity(ac)
            R.id.button_webp -> WebpActivity.openActivity(ac)
            R.id.button_glide -> {
                ARouter.getInstance()
                    .build("/demon/glide")
                    .navigation()
            }
            R.id.button_relative_layout -> RelativeLayoutActivity.openActivity(ac)
            R.id.button_volley -> VolleyActivity.openActivity(ac)
            R.id.button_exception -> ExceptionActivity.openActivity(ac)
            R.id.button_serializable -> SerializableActivity.openActivity(ac)
            R.id.button_drag -> DragActivity.openActivity(ac)
            R.id.button_region -> RegionActivity.openActivity(ac)
            R.id.button_data_binding -> {
                dailyJob.priority++
            }
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
