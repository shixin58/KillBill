package com.bride.demon;

import java.util.HashSet;
import java.util.Set;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Created by shixin on 2018/9/26.
 */
public class CellScrollHolder {

    private Set<RecyclerView> mSet = new HashSet<>();

    private int offset, position;

    public CellScrollHolder() {

    }

    public void register(RecyclerView recyclerView) {
        ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(position, offset);
        mSet.add(recyclerView);
    }

    public void unregister(RecyclerView recyclerView) {
        mSet.remove(recyclerView);
    }

    public void clear() {
        mSet.clear();
    }

    public void notifyScrollBy(int dx, int dy) {
        for(RecyclerView recyclerView : mSet) {
            recyclerView.scrollBy(dx, dy);
        }
    }

    public void notifyScrollTo(int x, int y) {
        for(RecyclerView recyclerView : mSet) {
            recyclerView.scrollTo(x, y);
        }
    }

    public void notifyScrollToPositionWithOffset(int position, int offset) {
        for(RecyclerView recyclerView : mSet) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            layoutManager.scrollToPositionWithOffset(position, offset);
        }
    }

    public void recordCurrentPosition(int offset, int position) {
        this.offset = offset;
        this.position = position;
    }
}
