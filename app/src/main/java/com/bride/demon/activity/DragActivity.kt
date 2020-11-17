package com.bride.demon.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bride.demon.R
import com.bride.ui_lib.BaseActivity

/**
 * <p>Created by shixin on 11/17/20.
 */
class DragActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag)
    }

    companion object {

        fun openActivity(ctx: Context) {
            val intent = Intent(ctx, DragActivity::class.java)
            ctx.startActivity(intent)
        }
    }
}