package com.bride.demon.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * <p>Created by shixin on 2018/9/26.
 */
public class DispatchFrameLayout extends FrameLayout {

    private DispatchTouchEventListener mDispatchTouchEventListener;

    public DispatchFrameLayout(@NonNull Context context) {
        super(context);
    }

    public DispatchFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mDispatchTouchEventListener!=null)
            mDispatchTouchEventListener.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return true;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setDispatchTouchEventListener(DispatchTouchEventListener dispatchTouchEventListener) {
        this.mDispatchTouchEventListener = dispatchTouchEventListener;
    }

    public interface DispatchTouchEventListener {
        void dispatchTouchEvent(MotionEvent ev);
    }
}
