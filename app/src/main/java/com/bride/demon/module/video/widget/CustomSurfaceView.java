package com.bride.demon.module.video.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Created by shixin on 2018/9/12.
 */
public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = CustomSurfaceView.class.getSimpleName();

    private SurfaceHolder mHolder;
    private final Paint mBgPaint = new Paint();
    private final Paint mContentPaint = new Paint();
    private ExecutorService mExecutorService;

    public CustomSurfaceView(Context context) {
        super(context);
        init();
    }
    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);

        mBgPaint.setColor(Color.BLUE);
        mContentPaint.setColor(Color.RED);
        mContentPaint.setTextSize(20);
    }

    @Override public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        mHolder.unlockCanvasAndPost(canvas);

        mExecutorService = new ThreadPoolExecutor(4, 8,
                1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(128));
        mExecutorService.execute(new ECGTask());
        mExecutorService.execute(new HeartRateTask());
        mExecutorService.execute(new WeightTask());
        mExecutorService.execute(new TestTask());
    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override public void surfaceDestroyed(SurfaceHolder holder) {
        mExecutorService.shutdownNow();
    }

    private class ECGTask implements Runnable {
        @Override
        public void run() {
            // 绘制过程
            for(int i=1; i<=10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // intercept信号，停止绘制，退出循环，终止线程
                    break;
                }
                Rect dirty = new Rect(i*30, 0, (i+1)*30, 200);
                // Surface$CompatibleCanvas. 多个异步任务操作同一个Canvas。一个任务对Canvas上锁，其他任务等待
                Canvas canvas = mHolder.lockCanvas(dirty);
                Log.i(TAG, "ECGTask "+canvas);
                if(canvas == null) break;
                canvas.drawRect(dirty, mBgPaint);
                canvas.drawText(""+i, i*30, 100, mContentPaint);
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private class HeartRateTask implements Runnable {
        @Override
        public void run() {
            try {
                // 绘制过程
                for(int i=1; i<=10; i++) {
                    Thread.sleep(1500);
                    Rect dirty = new Rect(i*40, 200, (i+1)*40, 400);
                    Canvas canvas = mHolder.lockCanvas(dirty);
                    Log.i(TAG, "HeartRateTask "+canvas);
                    if(canvas == null) break;
                    canvas.drawRect(dirty, mBgPaint);
                    canvas.drawText(""+i*2, i*40, 300, mContentPaint);
                    mHolder.unlockCanvasAndPost(canvas);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class WeightTask implements Runnable {
        @Override
        public void run() {
            // 绘制过程
            for(int i=1; i<=10; i++) {
                SystemClock.sleep(2000L);
                if (Thread.interrupted()) break;
                Rect dirty = new Rect(i*50, 400, (i+1)*50, 600);
                Canvas canvas = mHolder.lockCanvas(dirty);
                Log.i(TAG, "WeightTask "+canvas);
                if(canvas == null) break;
                canvas.drawRect(dirty, mBgPaint);
                canvas.drawText(""+i*3, i*50, 500, mContentPaint);
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private class TestTask implements Runnable {
        @Override
        public void run() {
            Log.i(TAG, "TestTask start");
            int i = 0;
            int count = 0;
            while (true) {
                i = i+3;
                if (i == 999999999) {
                    i = 0;
                    count = count+1;
                    Log.i(TAG, "TestTask is running! "+count);
                }
                // new ThreadDeath -> throw UnsupportedOperationException
                // 抛异常终止工作线程，不会终止主进程
                if (count > 50) Thread.currentThread().stop();
            }
        }
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure");
    }

    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout");
    }

    // 未调用onDraw
    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw "+canvas);
    }

    @Override public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        Log.i(TAG, "onDrawForeground "+canvas);
    }
}
