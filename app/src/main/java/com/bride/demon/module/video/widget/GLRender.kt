package com.bride.demon.module.video.widget

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import com.bride.demon.Finals
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * <p>Created by shixin on 2019-10-21.
 */
class GLRender : GLSurfaceView.Renderer {

    override fun onDrawFrame(gl: GL10?) {
        Log.d(Finals.TAG_GL_SURFACE_VIEW, "onDrawFrame")
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(Finals.TAG_GL_SURFACE_VIEW, "onSurfaceChanged")
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(Finals.TAG_GL_SURFACE_VIEW, "onSurfaceCreated")
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    }
}