package com.bride.demon.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

import com.bride.baselib.ResUtils
import com.bride.demon.DemonApplication
import com.bride.demon.R

import java.util.Collections
import java.util.LinkedList
import java.util.Random

/**
 * 直播页面点赞特效，采用SurfaceView绘制。The point is mastering the algorithm
 * 与普通控件使用方法类似，点赞是只需要调用startBomb()即可。
 */
class BombView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private val mHolder: SurfaceHolder = holder
    private val mDrawTask: DrawTask // 绘制UI的线程
    private val mPaint: Paint // 绘制需要使用的画刷

    private var mFuseView: FuseView? = null // 引导的view

    private var mIsDismiss = false //是否处于消失阶段

    private var mWidth: Int = 0 // 控件的宽度
    private var mHeight: Int = 0 // 控件的高度

    private var mBombX: Int = 0 // 旋转的中心X
    private var mBombY: Int = 0 // 旋转的中心Y

    private var dx = 0 // 引导view在坐抛物线运动时在x轴的增量

    private val mDrawables: Array<Bitmap?> // 存放需要展示的图
    private val mDrawableResIDs: IntArray // 存放需要展示的图

    private val mBubbles = Collections.synchronizedList(LinkedList<Bubble>()) // 用于存放点赞信息

    private val mRandom = Random() // 用于产生随机数

    private val randBitmap: Bitmap
        get() = BitmapFactory.decodeResource(resources, mDrawableResIDs[mRandom.nextInt(mDrawableResIDs.size)])

    init {
        mHolder.addCallback(this)
        mHolder.setKeepScreenOn(true)
        mHolder.setFormat(PixelFormat.TRANSPARENT)
        mPaint = Paint()
        mDrawTask = DrawTask()

        mDrawableResIDs = intArrayOf(R.drawable.reward_8, R.drawable.reward_1, R.drawable.reward_3, R.drawable.reward_2, R.drawable.reward_5, R.drawable.reward_4, R.drawable.reward_7, R.drawable.reward_6)
        mDrawables = arrayOfNulls(mDrawableResIDs.size)
    }

    internal fun drawItem(canvas: Canvas?) {
        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR) // 清空界面

            if (mFuseView == null) {
                return
            }

            if (mFuseView!!.y + mFuseView!!.bitmap!!.height / 2 > mBombY) {
                mPaint.alpha = 255
                if (mFuseView!!.x + mFuseView!!.bitmap!!.width / 2 > mWidth / 2) { // 抛物线
                    mFuseView!!.scale = dx * 1.5f / mWidth + 1f

                    canvas.save()
                    mFuseView!!.x = mWidth * 7 / 8 - dx
                    mFuseView!!.y = getY(dx.toFloat()).toInt()
                    canvas.scale(mFuseView!!.scale, mFuseView!!.scale, (mFuseView!!.x + mFuseView!!.bitmap!!.width / 2).toFloat(), (mFuseView!!.y + mFuseView!!.bitmap!!.height / 2).toFloat())
                    canvas.drawBitmap(mFuseView!!.bitmap!!, mFuseView!!.x.toFloat(), mFuseView!!.y.toFloat(), mPaint)
                    canvas.restore()
                    dx += ResUtils.dp2px(4f)
                } else { // 直线上升
                    canvas.save()
                    canvas.scale(mFuseView!!.scale, mFuseView!!.scale, (mFuseView!!.x + mFuseView!!.bitmap!!.width / 2).toFloat(), (mFuseView!!.y + mFuseView!!.bitmap!!.height / 2).toFloat())
                    canvas.drawBitmap(mFuseView!!.bitmap!!, mFuseView!!.x.toFloat(), mFuseView!!.y.toFloat(), mPaint)
                    canvas.restore()
                    mFuseView!!.y -= ResUtils.dp2px(5f)
                }
                postDelayed(mDrawTask, 5)

            } else {
                if (!mIsDismiss) {
                    mIsDismiss = mBubbles.size > 0 && mBubbles[0].top < mWidth * 9 / 20
                }

                for (i in mBubbles.indices.reversed()) { // 绘制气泡
                    drawBubble(canvas, mBubbles[i])
                }

                // 大爱心放大消失
                mFuseView!!.x = mBombX - mFuseView!!.bitmap!!.width / 2
                mFuseView!!.y = mBombY - mFuseView!!.bitmap!!.height / 2
                mFuseView!!.scale += 0.2f
                mFuseView!!.alpha = ((5 - mFuseView!!.scale) * 85).toInt()// ps:85 = 255/3 根据缩放倍数来计算透明度
                if (mFuseView!!.alpha > 0) {
                    mPaint.alpha = mFuseView!!.alpha
                    canvas.save()
                    canvas.scale(mFuseView!!.scale, mFuseView!!.scale, mBombX.toFloat(), mBombY.toFloat())
                    canvas.drawBitmap(mFuseView!!.bitmap!!, mFuseView!!.x.toFloat(), mFuseView!!.y.toFloat(), mPaint)
                    canvas.restore()
                }
                if (mBubbles.size > 0) {
                    postDelayed(mDrawTask, 10)
                } else {
                    release()
                }
            }
        }
    }

    private fun drawBubble(canvas: Canvas, bubble: Bubble) {
        if (bubble.top + bubble.bitmap.height / 2 > mWidth / 2) {
            bubble.top = bubble.top - ResUtils.dp2px(2f)
            return
        }

        if (mIsDismiss) {
            bubble.alpha -= 12
            if (bubble.alpha < 0) {
                mBubbles.remove(bubble)
                return
            }
        }
        bubble.top -= ResUtils.dp2px(0.5f).toFloat()
        if (bubble.scale < 1.2f) {
            bubble.scale += 0.015f
        }

        mPaint.alpha = bubble.alpha
        canvas.save()
        canvas.scale(bubble.scale, bubble.scale, bubble.left + bubble.bitmap.width / 2, bubble.top + bubble.bitmap.height / 2)
        canvas.rotate(bubble.rotate.toFloat(), mBombX.toFloat(), mBombY.toFloat())
        canvas.drawBitmap(bubble.bitmap, bubble.left, bubble.top, mPaint)
        canvas.restore()
    }

    /**
     * 开始绘制爆炸彩蛋，如果上一个效果还没结束，则不处理新的
     */
    fun startBomb() {
        if (mFuseView != null) {
            return
        }
        initFuseView()
        generateBubble(MAX_BUBBLE_COUNT)
        dx = 0
        mIsDismiss = false
        DemonApplication.exec.execute(mDrawTask)
    }

    /**
     * 初始化引导
     */
    private fun initFuseView() {
        mFuseView = FuseView()
        mFuseView!!.x = mWidth * 7 / 8
        mFuseView!!.y = mHeight * 17 / 20
        mFuseView!!.bitmap = BitmapFactory.decodeResource(resources, R.drawable.reward_5)
    }

    /**
     * 添加点赞，会对传入的个数进行处理
     * @param count
     */
    private fun generateBubble(count: Int) {
        for (i in 0 until count) {
            val bubble = Bubble()
            bubble.bitmap = randBitmap
            bubble.alpha = 155 + mRandom.nextInt(100)
            bubble.scale = 0.6f + mRandom.nextFloat() * 0.4f

            bubble.rotate = 360 * i / count // 由于绘制时还需要缩放，不然可以使用增量旋转，即不用每次都restore
            if (bubble.rotate > 180) { // 反向旋转
                bubble.rotate = bubble.rotate - 360
            }
            bubble.left = (mWidth / 2 - bubble.bitmap.width / 2).toFloat()

            var offset = 0f
            if (i % 2 == 0) {
                offset = mWidth * 0.1f + mWidth.toFloat() * 0.15f * i.toFloat() / count
            }
            if (i % 3 == 0) {
                offset = mWidth * 0.25f + mWidth.toFloat() * 0.15f * i.toFloat() / count
            }
            if (i % 5 == 0) {
                offset = mWidth * 0.4f + mWidth.toFloat() * 0.12f * mRandom.nextFloat()
            }
            bubble.top = offset - bubble.bitmap.height
            mBubbles.add(0, bubble)
        }
        mBubbles[0].top = mWidth * 0.52f - ResUtils.dp2px(5f) // 以第一个气泡的位置判断是否该dismiss
        mBubbles[0].alpha = 1 // 防止正在消失时突然显现出来
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        this.mWidth = width
        this.mHeight = height

        mBombX = mWidth / 2
        mBombY = mWidth / 2
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    internal inner class Bubble {
        var bitmap: Bitmap = randBitmap

        var scale = 1.0f // 缩放
        var top = 0f // 偏移
        var left = 0f // 偏移
        var rotate = 0 // 旋转
        var alpha = 255 // 透明度

    }

    internal inner class FuseView {
        var bitmap: Bitmap? = null
        var scale: Float = 0.toFloat()
        var alpha: Int = 0
        var x: Int = 0
        var y: Int = 0
    }

    // 抛物线
    private fun getY(x: Float): Float {
        return mHeight * 10 / 11 - 0.009f * x * x
    }

    fun release() {
        mFuseView = null // 防止过度绘制
        for (bitmap in mDrawables) {
            if (bitmap != null && !bitmap.isRecycled) {
                bitmap.recycle()
            }
        }
    }

    private inner class DrawTask : Runnable {

        override fun run() {
            val c = mHolder.lockCanvas()
            drawItem(c)
            mHolder.unlockCanvasAndPost(c)
        }
    }

    companion object {

        private const val MAX_BUBBLE_COUNT = 90
    }
}