package com.bride.demon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.bride.demon.R
import com.bride.demon.R.layout
import com.bride.demon.module.video.widget.CustomView
import com.bride.ui_lib.BaseFragment
import timber.log.Timber
import java.util.Timer
import java.util.TimerTask

/**
 * 自定义View展示1个倒计时countdown。
 * A placeholder fragment containing a simple view.
 */
class PlatformFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.fragment_platform, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(rootView: View) {
        val customView = rootView.findViewById<CustomView>(R.id.my_view)
        // 主线程刷新
        customView.cnt = CustomView.MAX_COUNT
        customView.invalidate()
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                var cnt = customView.cnt
                if (cnt > 0) {
                    // 工作线程刷新
                    customView.cnt = --cnt
                    customView.postInvalidate()
                } else {
                    timer.cancel()
                }
            }
        }, 1000L, 1000L)
        customView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                customView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                Timber.tag("PlatformFragment").i("onGlobalLayout() %s %s", customView.width, customView.height)
            }
        })
    }
}