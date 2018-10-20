package com.bride.demon.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
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
