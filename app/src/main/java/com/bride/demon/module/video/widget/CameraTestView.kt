package com.bride.demon.module.video.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.bride.baselib.ResUtils

class CameraTestView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private var mCamera: Camera = Camera()
    private var mMatrix: Matrix = Matrix()
    private var mPaint: Paint
    private var mRadius: Float = 0f

    init {
        setBackgroundColor(Color.BLUE)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.RED
        mRadius = if (isInEditMode) 60f * DENSITY_EDIT
            else TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60f, context.resources.displayMetrics)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mMatrix.reset()
        mCamera.save()
        if (isInEditMode) mCamera.translate(10f * DENSITY_EDIT, 50f * DENSITY_EDIT, -30f * DENSITY_EDIT)
        else mCamera.translate(ResUtils.dp2px(10f).toFloat(), ResUtils.dp2px(50f).toFloat(), ResUtils.dp2px(-30f).toFloat())
        mCamera.getMatrix(mMatrix)
        mCamera.restore()
        canvas.concat(mMatrix)

        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint)
    }

    companion object {
        const val DENSITY_EDIT = 3
    }
}