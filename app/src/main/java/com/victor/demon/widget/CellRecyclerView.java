package com.victor.demon.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <p>Created by shixin on 2018/9/28.
 */
public class CellRecyclerView extends RecyclerView {
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
    public boolean onTouchEvent(MotionEvent e) {
        // 不处理触摸事件
        return false;
//        return super.onTouchEvent(e);
    }
}
