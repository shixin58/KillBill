package com.bride.demon.module.video.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * <p>Created by shixin on 2020-01-13.
 */
class DiceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    init {
        holder.addCallback(this)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        setZOrderOnTop(true)
        setZOrderMediaOverlay(true)
    }

    fun reset() {
        val c = holder?.lockCanvas() ?: return
        drawAll(c)
        holder.unlockCanvasAndPost(c)
    }

    fun drawAll(c: Canvas) {
        c.drawColor(Color.WHITE)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        reset()
    }
}