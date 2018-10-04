package com.bride.demon;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Created by shixin on 2018/9/26.
 */
public class CellScrollHolder {
    private Set<RecyclerView> set = new HashSet<>();
    public CellScrollHolder() {

    }

    public void register(RecyclerView recyclerView) {
        set.add(recyclerView);
    }

    public void unregister(RecyclerView recyclerView) {
        set.remove(recyclerView);
    }

    public void clear() {
        set.clear();
    }

    public void notifyScrollBy(int dx, int dy) {
        for(RecyclerView recyclerView:set) {
            // 相对位置
            recyclerView.scrollBy(dx, dy);
        }
    }

    public void notifyScrollTo(int x, int y) {
        for(RecyclerView recyclerView:set) {
            // 绝对位置
            recyclerView.scrollTo(x, y);
            Log.i("notifyScrollTo", "scrollTo("+x+", "+y+")");
        }
    }

    // 在模版RecyclerView的onScrolled中处理
    public void notifyScrollToPositionWithOffset(int position, int offset) {
        for(RecyclerView recyclerView:set) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            layoutManager.scrollToPositionWithOffset(position, offset);
        }
    }
}
