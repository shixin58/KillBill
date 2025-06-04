package com.bride.demon.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.bride.demon.R

class CameraRotateTestView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
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
        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60f, context.resources.displayMetrics)
    }

    override fun onDraw(canvas: Canvas) {
        mMatrix.reset()
        mCamera.save()
//        mCamera.rotateX(60f)
//        mCamera.rotateY(60f)
        mCamera.rotateZ(60f)
        mCamera.getMatrix(mMatrix)
        mCamera.restore()

        mMatrix.preTranslate(-width / 2f, -height / 2f)
        mMatrix.postTranslate(width / 2f, height / 2f)

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        options.inSampleSize = calculateInSampleSize(options, width / 2, height / 2)
        options.inJustDecodeBounds = false
        canvas.drawBitmap(BitmapFactory.decodeResource(resources, R.drawable.city, options), mMatrix, mPaint)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val width = options.outWidth
        val height = options.outHeight
        var inSampleSize = 1
        if (width > reqWidth || height > reqHeight) {
            val halfWidth = width / 2
            val halfHeight = height / 2
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}