package com.yiche.example.newestversiondemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;

/**
 * <p>Created by shixin on 2018/3/23.
 */
public class DrawUtils {

    public static void drawTag(Context context, View view, String text){

        if(TextUtils.isEmpty(text)){
            return;
        }

        Bitmap bitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint trapezoidPaint = new Paint();
        trapezoidPaint.setColor(Color.parseColor("#FF7275"));
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(80, 80);
        path.lineTo(80, 40);
        path.lineTo(40, 0);
        path.close();
        canvas.drawPath(path, trapezoidPaint);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(24);
        paint.setAntiAlias(true);
        Rect textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);
        canvas.rotate(45, 40, 40);
        canvas.drawText(text, 40-textRect.centerX(), 40-paint.descent(), paint);
        canvas.rotate(-45, 40, 40);

        view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bitmap));
    }
}
