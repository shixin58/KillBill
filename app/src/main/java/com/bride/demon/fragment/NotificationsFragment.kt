package com.bride.demon.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bride.demon.activity.BlankActivity.Companion.openActivity
import com.bride.demon.activity.FlutterFragmentDemoActivity
import com.bride.demon.activity.PlatformActivity.Companion.openActivityForResult
import com.bride.demon.databinding.FragmentNotificationsBinding
import com.bride.demon.module.video.activity.AudioRecordActivity
import com.bride.demon.module.video.activity.Camera3DActivity
import com.bride.demon.module.video.activity.DiceActivity
import com.bride.demon.module.video.activity.GLActivity
import com.bride.demon.module.video.activity.LiveCameraActivity
import com.bride.demon.module.video.activity.VideoViewActivity
import com.bride.ui_lib.BaseFragment
import io.flutter.embedding.android.FlutterActivity

class NotificationsFragment : BaseFragment() {
    companion object {
        fun newInstance(): NotificationsFragment {
            return NotificationsFragment()
        }
    }

    private lateinit var mBinding: FragmentNotificationsBinding
    private var blankClicked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("blankClicked", blankClicked)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            blankClicked = savedInstanceState.getBoolean("blankClicked")
        }
        mBinding.fragment = this
    }

    fun onClick(v: View) {
        when(v) {
            mBinding.buttonBase -> {
                openActivity(activity)
                blankClicked = true
            }
            mBinding.buttonCustomView -> openActivityForResult(this, 1)
            mBinding.buttonTextureView -> LiveCameraActivity.openActivity(activity)
            mBinding.buttonAudioRecord -> AudioRecordActivity.openActivity(activity)
            mBinding.btnVideo -> VideoViewActivity.openActivity(activity)
            mBinding.btnGlsurfaceview -> GLActivity.openActivity(requireActivity())
            mBinding.btnCamera3d -> Camera3DActivity.openActivity(requireActivity())
            mBinding.btnDice -> DiceActivity.openActivity(requireActivity())
            mBinding.btnFlutter -> {
                // 1）假定了你的 Dart 代码入口是调用 main()，并且你的 Flutter 初始路由是 ‘/’
                // Flutter.createView/createFragment已过时
                // Flutter通过View.of(context).platformDispatcher.defaultRouteName获取路由名，window.defaultRouteName(dart:ui)已过时
//            startActivity(FlutterActivity.createDefaultIntent(requireActivity()));

                // 2）打开一个自定义 Flutter 初始路由的 FlutterActivity。
                // 工厂方法 withNewEngine() 可以用于配置一个 FlutterActivity，它会在内部创建一个属于自己的 FlutterEngine 实例，这会有一个明显的初始化时间
                startActivity(
                    FlutterActivity.withNewEngine()
                        .initialRoute("/")
                        .build(requireActivity())
                )

                // 3）使用缓存的 FlutterEngine。在 Application 类中预先初始化一个 FlutterEngine
                /*startActivity(
                        FlutterActivity.withCachedEngine("my_engine_id")
                                .build(requireActivity())
                );*/
            }
            mBinding.btnFlutterFragment -> startActivity(Intent(activity, FlutterFragmentDemoActivity::class.java))
        }
    }
}