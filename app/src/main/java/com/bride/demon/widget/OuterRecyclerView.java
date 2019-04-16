package com.bride.demon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Created by shixin on 2019/4/16.
 */
public class OuterRecyclerView extends RecyclerView {
    private static final String TAG = OuterRecyclerView.class.getSimpleName();

    public OuterRecyclerView(@NonNull Context context) {
        super(context);
    }

    public OuterRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OuterRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent - "+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean handled = super.onTouchEvent(e);
        Log.i(TAG, "onTouchEvent - "+e.getAction()+" - "+handled);
        return handled;
    }
}
