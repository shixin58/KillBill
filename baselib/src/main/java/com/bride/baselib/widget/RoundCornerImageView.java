package com.bride.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bride.baselib.R;

/**
 * <p>Created by shixin on 2018/4/3.
 */
public class RoundCornerImageView extends AppCompatImageView {

    private Path path = new Path();
    private RectF rectF = new RectF();
    private float[] radii = new float[8];
    private DrawFilter drawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG);
    private float mStrokeWidth = 0;

    public RoundCornerImageView(Context context) {
        super(context);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView, 0, 0);
            // 设置圆角
            float radius = a.getDimension(R.styleable.RoundCornerImageView_corner_radius, 0f);
            for(int i=0;i<radii.length;i++) {
                radii[i] = radius;
            }
            // 设置边框
            mStrokeWidth = a.getDimension(R.styleable.RoundCornerImageView_stroke_width, 0f);
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rectF.set(mStrokeWidth, mStrokeWidth, this.getWidth()- mStrokeWidth, this.getHeight()- mStrokeWidth);
        path.addRoundRect(rectF, radii, Path.Direction.CW);
        canvas.setDrawFilter(drawFilter);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}