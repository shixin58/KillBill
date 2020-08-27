package com.bride.demon.module.video.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.bride.baselib.ResUtils
import com.bride.demon.R

/**
 * <p>Created by shixin on 2020-01-13.
 */
class DiceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private var mCamera: Camera = Camera()
    private var mMatrix: Matrix = Matrix()

    private val mCubePaint by lazy {
        Paint().apply {
            color = Color.parseColor("#FFFFFF00")
            style = Paint.Style.FILL
        }
    }

    private val mBitmapPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            isDither = true
        }
    }

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

        drawBounds(c)
    }

    private fun drawBounds(c: Canvas) {
        val bm = BitmapFactory.decodeResource(resources, R.drawable.brave)
        for (i in 0 until CUBE_NUM_H) {
            mMatrix.reset()
            mCamera.save()
            mCamera.rotateZ(45f)
            mCamera.rotateX(30f)
            mCamera.rotateY(30f)
            mCamera.translate(AREA_START.toFloat() + i * (CUBE_GAP + CUBE_WIDTH), 0f, 0f)
            mCamera.getMatrix(mMatrix)
            mCamera.restore()

            mMatrix.preTranslate(-width / 2f, -height / 2f)
            mMatrix.postTranslate(width / 2f, height / 2f)

            c.drawBitmap(bm, mMatrix, mBitmapPaint)
        }
        for (i in 0 until (CUBE_NUM_V - 2)) {
            mMatrix.reset()
            mCamera.save()
            mCamera.rotateZ(45f)
            mCamera.rotateX(30f)
            mCamera.rotateY(30f)
            mCamera.translate(AREA_START.toFloat(), (i + 1) * (CUBE_GAP + CUBE_WIDTH).toFloat(), 0f)
            mCamera.getMatrix(mMatrix)
            mCamera.restore()

            mMatrix.preTranslate(-width / 2f, -height / 2f)
            mMatrix.postTranslate(width / 2f, height / 2f)

            c.drawBitmap(bm, mMatrix, mBitmapPaint)
        }
        for (i in 0 until (CUBE_NUM_V - 2)) {
            mMatrix.reset()
            mCamera.save()
            mCamera.rotateZ(45f)
            mCamera.rotateX(30f)
            mCamera.rotateY(30f)
            mCamera.translate(AREA_START.toFloat() + (CUBE_NUM_H - 1) * (CUBE_GAP + CUBE_WIDTH), (i + 1) * (CUBE_GAP + CUBE_WIDTH).toFloat(), 0f)
            mCamera.getMatrix(mMatrix)
            mCamera.restore()

            mMatrix.preTranslate(-width / 2f, -height / 2f)
            mMatrix.postTranslate(width / 2f, height / 2f)

            c.drawBitmap(bm, mMatrix, mBitmapPaint)
        }
        for (i in 0 until CUBE_NUM_H) {
            mMatrix.reset()
            mCamera.save()
            mCamera.rotateZ(45f)
            mCamera.rotateX(30f)
            mCamera.rotateY(30f)
            mCamera.translate(AREA_START.toFloat() + i * (CUBE_GAP + CUBE_WIDTH), (CUBE_NUM_V - 1) * (CUBE_GAP + CUBE_WIDTH).toFloat(), 0f)
            mCamera.getMatrix(mMatrix)
            mCamera.restore()

            mMatrix.preTranslate(-width / 2f, -height / 2f)
            mMatrix.postTranslate(width / 2f, height / 2f)

            c.drawBitmap(bm, mMatrix, mBitmapPaint)
        }
    }

    private fun getCubeBitmap(): Bitmap {
        val rect = Rect(AREA_START, 0, AREA_START + CUBE_WIDTH, CUBE_WIDTH)
        val bm = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        canvas.drawRect(rect, mCubePaint)
        return bm
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        reset()
    }

    companion object {
        val CUBE_WIDTH = ResUtils.dp2px(30f)
        val CUBE_GAP = ResUtils.dp2px(2f)
        val AREA_START = ResUtils.dp2px((360 - (30 * 8 + 2 * 7)) / 2f)
        const val CUBE_NUM_H = 8
        const val CUBE_NUM_V = 7
    }
}