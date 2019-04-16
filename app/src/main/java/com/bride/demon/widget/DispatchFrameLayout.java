package com.bride.demon.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * <p>Created by shixin on 2018/9/26.
 */
public class DispatchFrameLayout extends FrameLayout {
    private static final String TAG = DispatchFrameLayout.class.getSimpleName();

    private DispatchTouchEventListener mDispatchTouchEventListener;

    public DispatchFrameLayout(@NonNull Context context) {
        super(context);
    }

    public DispatchFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent - "+ev.getAction());
        if(mDispatchTouchEventListener != null)
            mDispatchTouchEventListener.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        boolean handled = super.onTouchEvent(event);
        Log.i(TAG, "onTouchEvent - "+event.getAction()+" - "+handled);
        return handled;
    }

    public void setDispatchTouchEventListener(DispatchTouchEventListener dispatchTouchEventListener) {
        this.mDispatchTouchEventListener = dispatchTouchEventListener;
    }

    public interface DispatchTouchEventListener {
        void dispatchTouchEvent(MotionEvent ev);
    }
}
