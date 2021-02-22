package com.bride.demon.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bride.demon.R
import com.bride.ui_lib.BaseActivity

/**
 * <p>Created by shixin on 2/22/21.
 */
class RegionActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region)
    }

    companion object {
        fun openActivity(ctx: Context) {
            val intent = Intent(ctx, RegionActivity::class.java)
            ctx.startActivity(intent)
        }
    }
}