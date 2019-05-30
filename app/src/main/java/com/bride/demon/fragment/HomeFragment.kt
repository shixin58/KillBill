package com.bride.demon.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bride.baselib.BaseFragment
import com.bride.demon.R
import com.bride.demon.activity.*

class HomeFragment : BaseFragment(), View.OnClickListener {

    private var blankClicked = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<View>(R.id.button_base)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_fragment)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_texture_view)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_audio_record)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_recycler_view)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_touch)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_nested_list)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_upload)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_webp)?.setOnClickListener(this)
        view?.findViewById<TextView>(R.id.button_glide)?.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_relative_layout)!!.setOnClickListener(this)
        view?.findViewById<View>(R.id.button_volley)!!.setOnClickListener(this)
        if (savedInstanceState != null) {
            blankClicked = savedInstanceState.getBoolean("blankClicked")
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("blankClicked", blankClicked)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.button_base -> {
                BlankActivity.openActivity(activity)
                blankClicked = true
            }
            R.id.button_fragment -> PlatformActivity.openActivityForResult(this, 1)
            R.id.button_texture_view -> LiveCameraActivity.openActivity(activity)
            R.id.button_audio_record -> AudioRecordActivity.openActivity(activity)
            R.id.button_recycler_view -> RecyclerViewActivity.openActivity(activity)
            R.id.button_touch -> TouchActivity.openActivity(activity, 0)
            R.id.button_nested_list -> NestedListActivity.openActivity(activity)
            R.id.button_upload -> UploadActivity.openActivity(activity)
            R.id.button_webp -> WebpActivity.openActivity(activity)
            R.id.button_glide -> GlideActivity.openActivity(activity)
            R.id.button_relative_layout -> RelativeLayoutActivity.openActivity(activity)
            R.id.button_volley -> VolleyActivity.openActivity(activity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 接收返回
        Log.i("Victor", "requestCode-$requestCode")
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
