package com.bride.demon.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * <p>Created by shixin on 2/22/21.
 */
class CustomRegionView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    val mPaint = Paint().apply {
        this.color = Color.RED
        this.style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    val mRPaint = Paint().apply {
        this.color = Color.GREEN
        this.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?:return

        // Path用法
        val path = Path()
        path.moveTo(100f, 100f)
        path.lineTo(200f, 200f)
        path.arcTo(0f, 300f, 300f, 500f, 0f, 180f, false)
        canvas.drawPath(path, mPaint)

        path.reset()
        path.moveTo(100f, 600f)
        // 二次Bezier曲线
        path.quadTo(200f, 700f, 300f, 600f)
        canvas.drawPath(path, mPaint)

        path.reset()
        path.moveTo(100f, 700f)
        // 三次Bezier曲线
        path.cubicTo(200f, 800f, 300f, 700f, 400f, 800f)
        canvas.drawPath(path, mPaint)

        path.reset()
        val oval = RectF(100f, 800f, 300f, 1000f)
        // 圆角矩形
        path.addRoundRect(oval, 25f, 25f, Path.Direction.CCW)
        path.addCircle(300f, 1000f, 80f, Path.Direction.CCW)
        canvas.drawPath(path, mPaint)

        path.reset()
        path.addRect(100f, 1100f, 300f, 1300f, Path.Direction.CCW)
        val path2 = Path()
        // 椭圆x^2/a^2 + y^2/b^2 <= 1
        path2.addOval(200f, 1250f, 400f, 1400f, Path.Direction.CCW)
        path.op(path2, Path.Op.UNION)
        canvas.drawPath(path, mPaint)

        // Region用法
        // 描绘单个矩形区域或多个矩形的组合区域，不方便描述边界
        val rect1 = Rect(300, 100, 600, 400)
        val rect2 = Rect(500, 300, 800, 600)
        canvas.drawRect(rect1, mPaint)
        canvas.drawRect(rect2, mPaint)

        val region1 = Region(rect1)
        val region2 = Region(rect2)
        region1.op(region2, Region.Op.UNION)
        drawRegion(canvas, region1, mRPaint)
    }

    private fun drawRegion(c: Canvas, r: Region, p: Paint) {
        val ri = RegionIterator(r)
        val rect = Rect()
        while (ri.next(rect)) {
            c.drawRect(rect, p)
        }
    }
}