package com.bride.demon.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bride.demon.R
import com.bride.ui_lib.BaseActivity

class Camera3DActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_3d)
    }

    companion object {
        fun openActivity(ctx: Context) {
            val intent = Intent(ctx, Camera3DActivity::class.java)
            ctx.startActivity(intent)
        }
    }
}