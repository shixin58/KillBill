package com.bride.demon.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

/**
 * <p>Created by shixin on 11/17/20.
 */
class DragHelperGridView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ViewGroup(context, attrs, defStyleAttr) {
    private var mViewDragHelper: ViewDragHelper = ViewDragHelper.create(this, DragCallback())

    companion object {
        private const val COLUMNS = 4
        private const val ROWS = 3
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = specWidth / COLUMNS
        val childHeight = specHeight / ROWS

        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY))

        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val cnt = childCount
        var childLeft: Int
        var childTop: Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for (i in 0 until cnt) {
            val child = getChildAt(i)
            childLeft = i % COLUMNS * childWidth
            childTop = i / COLUMNS * childHeight
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mViewDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewDragHelper.processTouchEvent(event)
        return true
    }

    inner class DragCallback: ViewDragHelper.Callback() {

        private var captureLeft: Float = 0f
        private var captureTop: Float = 0f

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                capturedChild.elevation = elevation + 1
            }
            captureLeft = capturedChild.left.toFloat()
            captureTop = capturedChild.top.toFloat()
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            mViewDragHelper.settleCapturedViewAt(captureLeft.toInt(), captureTop.toInt())
            postInvalidateOnAnimation()
        }

        override fun onViewDragStateChanged(state: Int) {
            if (state == ViewDragHelper.STATE_IDLE) {
                val capturedView = mViewDragHelper.capturedView
                if (capturedView != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        capturedView.elevation = capturedView.elevation - 1
                    }
                }
            }
        }
    }

    override fun computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }
}