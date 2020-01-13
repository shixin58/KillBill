package com.bride.demon.module.video.activity

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.bride.baselib.ResUtils
import com.bride.demon.R
import com.bride.demon.module.video.widget.Rotate3dAnimation
import com.bride.ui_lib.BaseActivity

class Camera3DActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_3d)
    }

    fun onClick(v: View) {
        val outSize = Point()
        windowManager.defaultDisplay.getSize(outSize)
        val width = outSize.x
        val height = outSize.y
        val animation = Rotate3dAnimation(0f, 360f,
                ResUtils.dp2px(100f).toFloat(), ResUtils.dp2px(100f).toFloat(), 0f, true)
        animation.interpolator = AccelerateDecelerateInterpolator()
        animation.duration = 2000
        animation.fillAfter = true
        v.startAnimation(animation)
    }

    companion object {
        fun openActivity(ctx: Context) {
            val intent = Intent(ctx, Camera3DActivity::class.java)
            ctx.startActivity(intent)
        }
    }
}