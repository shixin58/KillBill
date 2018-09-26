package com.victor.demon;

import android.support.v7.widget.RecyclerView;

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

    public void notifyScroll(int dx, int dy) {
        for(RecyclerView recyclerView:set) {
            recyclerView.scrollBy(dx, dy);
        }
    }
}
