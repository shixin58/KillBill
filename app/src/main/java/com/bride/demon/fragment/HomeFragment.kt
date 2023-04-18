package com.bride.demon.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bride.demon.activity.*
import com.bride.demon.databinding.FragmentHomeBinding
import com.bride.demon.model.DailyJob
import com.bride.ui_lib.BaseFragment
import kotlin.random.Random

class HomeFragment : BaseFragment(), View.OnClickListener {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private lateinit var mBinding: FragmentHomeBinding

    private val random = Random(7)
    private val dailyJob = DailyJob().also {
        it.name = "Work"
        it.priority = 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.fragment = this
        mBinding.dailyJob = dailyJob
    }

    override fun onClick(v: View?) {
        val ac = activity?:return
        when(v) {
            mBinding.buttonRecyclerView -> RecyclerViewActivity.openActivity(ac)
            mBinding.buttonTouch -> TouchActivity.openActivity(ac, 0)
            mBinding.buttonNestedList -> NestedListActivity.openActivity(ac)
            mBinding.buttonUpload -> UploadActivity.openActivity(ac)
            mBinding.buttonWebp -> WebpActivity.openActivity(ac)
            mBinding.buttonGlide -> startActivity(Intent(ac, GlideActivity::class.java))
            mBinding.buttonRelativeLayout -> LayoutOptimizationActivity.openActivity(ac)
            mBinding.buttonVolley -> VolleyActivity.openActivity(ac)
            mBinding.buttonException -> ExceptionActivity.openActivity(ac)
            mBinding.buttonSerializable -> SerializableActivity.openActivity(ac)
            mBinding.buttonDrag -> DragActivity.openActivity(ac)
            mBinding.buttonRegion -> RegionActivity.openActivity(ac)
            mBinding.buttonDataBinding -> dailyJob.name = "Work-${random.nextInt(1000)}"
            mBinding.buttonDataBindingReverse -> {
                val name = "Work-${random.nextInt(1000)}"
                mBinding.info.text = name
                Toast.makeText(ac, "dailyJob.name: ${dailyJob.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
