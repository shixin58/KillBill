package com.victor.demon.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Created by shixin on 2018/9/12.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder mHolder;
    private Paint mPaint = new Paint();

    public MySurfaceView(Context context) {
        super(context);
        init();
    }
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);

        mPaint.setColor(Color.RED);
        mPaint.setTextSize(20);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ExecutorService executorService = new ThreadPoolExecutor(3, 5,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128));
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable2());
        executorService.execute(new MyRunnable3());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            // 绘制过程
            for(int i=1;i<=10;i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Canvas canvas = mHolder.lockCanvas(new Rect(i*30, 0, (i+1)*30, 200));
                canvas.drawText(""+i, i*30, 100, mPaint);
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private class MyRunnable2 implements Runnable {
        @Override
        public void run() {
            // 绘制过程
            for(int i=1;i<=10;i++) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Canvas canvas = mHolder.lockCanvas(new Rect(i*40, 200, (i+1)*40, 400));
                canvas.drawText(""+i*2, i*40, 300, mPaint);
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private class MyRunnable3 implements Runnable {
        @Override
        public void run() {
            // 绘制过程
            for(int i=1;i<=10;i++) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Canvas canvas = mHolder.lockCanvas(new Rect(i*50, 400, (i+1)*50, 600));
                canvas.drawText(""+i*3, i*50, 500, mPaint);
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
