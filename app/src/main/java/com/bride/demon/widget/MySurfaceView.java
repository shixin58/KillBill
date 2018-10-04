package com.bride.demon.widget;

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
    private Paint mBgPaint = new Paint();
    private Paint mPaint = new Paint();
    private ExecutorService mExecutorService;

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

        mBgPaint.setColor(Color.BLUE);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(20);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        mHolder.unlockCanvasAndPost(canvas);

        mExecutorService = new ThreadPoolExecutor(3, 5,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128));
        mExecutorService.execute(new MyRunnable());
        mExecutorService.execute(new MyRunnable2());
        mExecutorService.execute(new MyRunnable3());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mExecutorService.shutdownNow();
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
                Rect dirty = new Rect(i*30, 0, (i+1)*30, 200);
                Canvas canvas = mHolder.lockCanvas(dirty);
                if(canvas==null) break;
                canvas.drawRect(dirty, mBgPaint);
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
                Rect dirty = new Rect(i*40, 200, (i+1)*40, 400);
                Canvas canvas = mHolder.lockCanvas(dirty);
                if(canvas==null) break;
                canvas.drawRect(dirty, mBgPaint);
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
                Rect dirty = new Rect(i*50, 400, (i+1)*50, 600);
                Canvas canvas = mHolder.lockCanvas(dirty);
                if(canvas==null) break;
                canvas.drawRect(dirty, mBgPaint);
                canvas.drawText(""+i*3, i*50, 500, mPaint);
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
