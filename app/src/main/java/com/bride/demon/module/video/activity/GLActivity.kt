package com.bride.demon.module.video.activity

import android.content.Context
import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.Bundle
import com.bride.demon.R
import com.bride.demon.module.video.widget.GLRender
import com.bride.ui_lib.BaseActivity

/**
 * <p>Created by shixin on 2019-10-21.
 */
class GLActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gl)

        val glSurfaceView = findViewById<GLSurfaceView>(R.id.gl_surface_view)
        glSurfaceView.setEGLContextClientVersion(2)
        val glRenderer = GLRender()
        glSurfaceView.setRenderer(glRenderer)
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    companion object Companion {
        fun openActivity(context: Context) {
            val intent = Intent(context, GLActivity::class.java)
            context.startActivity(intent)
        }
    }
}