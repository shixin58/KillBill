package com.bride.demon.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * <p>Created by shixin on 2018/9/28.
 */
public class CellRecyclerView extends RecyclerView {
    private static final String TAG = CellRecyclerView.class.getSimpleName();

    public CellRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CellRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CellRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // cell在分发时判断上下滚动，将MOVE转换为CANCEL。下次move事件分发，outer拦截并消费。
        Log.i(TAG, "dispatchTouchEvent - "+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean handled = true;
//        boolean handled = super.onTouchEvent(e);
        Log.i(TAG, "onTouchEvent - "+e.getAction()+" - "+handled);
        return handled;
    }
}
