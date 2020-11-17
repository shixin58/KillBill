package com.bride.demon.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.bride.demon.R
import kotlinx.android.synthetic.main.activity_drag.view.*

/**
 * <p>Created by shixin on 11/17/20.
 */
class DragContainerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {
    private var mViewDragHelper: ViewDragHelper = ViewDragHelper.create(this, DragCallback())
    private val mSymbolIds = arrayOf(
            R.id.symbol_box_01, R.id.symbol_box_02, R.id.symbol_box_03, R.id.symbol_box_04,
            R.id.symbol_box_05, R.id.symbol_box_06, R.id.symbol_box_07, R.id.symbol_box_08,
            R.id.symbol_box_09, R.id.symbol_box_10, R.id.symbol_box_11, R.id.symbol_box_12)

    companion object {
        private const val COLUMNS = 4
        private const val ROWS = 3
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
            return mSymbolIds.contains(child.id)
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