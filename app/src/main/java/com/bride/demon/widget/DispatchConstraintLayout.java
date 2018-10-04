package com.bride.demon.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <p>Created by shixin on 2018/9/26.
 */
public class DispatchConstraintLayout extends ConstraintLayout {
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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
//                return true;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
