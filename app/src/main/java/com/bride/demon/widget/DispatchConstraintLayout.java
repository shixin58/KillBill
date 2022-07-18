package com.bride.demon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * <p>Created by shixin on 2018/9/26.
 */
public class DispatchConstraintLayout extends ConstraintLayout {
    private static final String TAG = DispatchConstraintLayout.class.getSimpleName();

    public DispatchConstraintLayout(Context context) {
        super(context);
    }

    public DispatchConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent "+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent "+ev.getAction());
        return super.onInterceptTouchEvent(ev);
        // 手指滑出View边界，会不执行performClick()
        // ACTION_MOVE或ACTION_UP返回true的话，会发给子View1个ACTION_CANCEL
        /*switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                return true;
            default:
                return super.onInterceptTouchEvent(ev);
        }*/
        // 若返回true，onInterceptTouchEvent()拦截ACTION_DOWN事件，当前及之后的事件序列均由DispatchConstraintLayout消费、不再向下分发，之后的事件不再调用onInterceptTouchEvent()
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent "+event.getAction());
        return super.onTouchEvent(event);
        // 若返回true，子View能收到ACTION_DOWN事件，但后续事件不再向下分发
//        return true;
    }
}
