package com.bride.ui_lib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import com.bride.baselib.ResUtils;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 方形多色块
 * <p>项目名称：android-autoEasy
 * @author menggod
 * @date 2018/1/4.
 */
public class RatioSquareColorView extends AppCompatImageView {
    private String mColorString = "#ff0000,#0000ff,#00ff00";

    private Bitmap mMaskBitmap;
    private Canvas mMaskCanvas;

    private int mWidth;
    private int mHeight;

    public RatioSquareColorView(Context context) {
        super(context);
        initView();
    }

    public RatioSquareColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {

    }

    public void setStringColor(String colorString, int length) {
        if (colorString == null) {
            return;
        }
        mWidth = length;
        mHeight = length;

        if (mMaskBitmap == null && length != 0) {
            mMaskBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        }
        if (mMaskCanvas == null) {
            mMaskCanvas = new Canvas(mMaskBitmap);
        }

        String[] colorList = colorString.split(",");

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        if (colorList != null && colorList.length > 0) {
            int itemHeight = mHeight / (colorList.length);
            int startTop = 0;
            for (String s : colorList) {
                try {
                    paint.setColor(Color.parseColor(s));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mMaskCanvas.drawRect(0, startTop, mWidth, startTop + itemHeight, paint);
                startTop = startTop + itemHeight;
            }
//            drawOutWhiteSquare(mMaskCanvas, 1);
//            Bitmap bitmap = makeRoundCorner(mMaskBitmap);
            this.setImageBitmap(mMaskBitmap);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        drawOutWhiteSquare(mMaskCanvas, 1);

        Paint paint = new Paint();
        paint.setStrokeWidth(ResUtils.dp2px(2));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        Log.d("RatioSquareColorView", "getRight():" + getRight());
        Log.d("RatioSquareColorView", "getBottom():" + getBottom());
        canvas.drawRect(new RectF(0, 0, getRight()- getLeft(), getBottom()-getTop()), paint);
    }

    private void drawOutWhiteSquare(Canvas canvas, int strokeWidth) {
        Paint paint = new Paint();
        int parseColor = Color.parseColor("#00ff00");
        paint.setColor(parseColor);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        int w = this.getPaddingLeft() + this.getPaddingRight();
        int x = getWidth() - w;
        int r = x / 2;
        Toast.makeText(getContext(), (getLeft() + "---" + getRight() + "---" + getTop() + "---" + getBottom()), Toast.LENGTH_SHORT).show();

        canvas.drawRect(new RectF(0, 0, 90 - strokeWidth, 60 - strokeWidth), paint);
//        canvas.drawRect(new RectF(getLeft(), getTop(), getRight() ,getBottom()), paint);
    }

    public Bitmap makeRoundCorner(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height / 2;
        if (width > height) {
            left = (width - height) / 2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width) / 2;
            right = width;
            bottom = top + width;
            roundPx = width / 2;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
