package com.bride.demon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.bride.demon.CellScrollHolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Created by shixin on 2019/4/16.
 */
public class HeaderRecyclerView extends RecyclerView {

    private CellScrollHolder mHandler;

    public HeaderRecyclerView(@NonNull Context context) {
        super(context);
    }

    public HeaderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        View topView = getLayoutManager().getChildAt(0);// 获取可视的第一个view
        int offset = topView.getLeft(); //获取与该view的顶部的偏移量
        int position = getLayoutManager().getPosition(topView);// 得到该View的数组位置
        if (mHandler != null) {
            mHandler.notifyScrollToPositionWithOffset(position, offset);
            mHandler.recordCurrentPosition(offset, position);
        }
    }

    public void setScrollHandler(CellScrollHolder handler) {
        mHandler = handler;
    }
}
