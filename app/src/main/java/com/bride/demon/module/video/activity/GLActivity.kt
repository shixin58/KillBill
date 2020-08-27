package com.bride.demon.module.video.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bride.demon.R
import com.bride.demon.module.video.widget.FGLView
import com.bride.ui_lib.BaseActivity

/**
 * <p>Created by shixin on 2019-10-21.
 */
class GLActivity : BaseActivity() {

    private lateinit var glSurfaceView: FGLView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gl)

        glSurfaceView = findViewById(R.id.gl_surface_view)
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView.onPause()
    }

    companion object Companion {

        fun openActivity(context: Context) {
            val intent = Intent(context, GLActivity::class.java)
            context.startActivity(intent)
        }
    }
}