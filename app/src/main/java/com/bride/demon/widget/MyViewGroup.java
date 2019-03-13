package com.bride.demon.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * <p>Created by shixin on 2018/9/13.
 */
public class MyViewGroup extends ViewGroup implements View.OnClickListener {
    private static final String TAG = MyViewGroup.class.getSimpleName();

    public MyViewGroup(Context context) {
        super(context);
        init();
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 设置child大小和位置
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 先调用onTouch，后调用onTouchEvent
        // false未处理，传给上层onTouchEvent
        Log.i(TAG, "dispatchTouchEvent "+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // true直接onTouchEvent; false子类dispatchTouchEvent
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // true 表示 the event was handled in this view，接收之后的move和up事件
                Log.i(TAG, "onTouchEvent ACTION_DOWN true");
                return true;
            case MotionEvent.ACTION_MOVE:
                // 绝对位置和相对位置
                Log.i(TAG, "onTouchEvent ACTION_MOVE 坐标："+event.getRawX()+" - "+event.getRawY()+" - "+event.getX()+" - "+event.getY());
                // 触控点数，二维数组
                // points between this event and previous event
                if(event.getPointerCount()>0 && event.getHistorySize()>0){
                    Log.i(TAG, "onTouchEvent ACTION_MOVE 历史："+event.getPointerCount()+" - "+event.getHistorySize()
                            +" - "+event.getHistoricalX(event.getPointerCount()-1, event.getHistorySize()-1)
                            +" - "+event.getHistoricalY(event.getPointerCount()-1, event.getHistorySize()-1));
                }
                break;
            case MotionEvent.ACTION_UP:
                // 处理performClick()
//                performClick();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_OUTSIDE:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
        }
        // true消费不再向上传递
        boolean consumed = super.onTouchEvent(event);
        Log.i(TAG, "onTouchEvent "+event.getAction()+" "+consumed);
        return consumed;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick - ok");
    }
}
