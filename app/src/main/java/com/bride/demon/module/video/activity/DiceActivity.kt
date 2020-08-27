package com.bride.demon.module.video.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bride.demon.R
import com.bride.ui_lib.BaseActivity

/**
 * <p>Created by shixin on 2020-01-13.
 */
class DiceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice)
    }

    companion object {
        fun openActivity(ctx: Context) {
            val intent = Intent(ctx, DiceActivity::class.java)
            ctx.startActivity(intent)
        }
    }
}