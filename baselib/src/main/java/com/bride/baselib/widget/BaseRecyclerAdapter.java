package com.bride.baselib.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * <p>Created by shixin on 2018/4/19.
 */
public abstract class BaseRecyclerAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> implements View.OnClickListener {
    protected OnItemClickListener mOnItemClickListener;
    protected WeakReference<RecyclerView> mRecyclerView;

    abstract public void onBindVH(V holder, int position);

    public abstract V onCreateVH(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        if(mOnItemClickListener!=null) {
            holder.itemView.setOnClickListener(this);
        }
        onBindVH(holder, position);
    }

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mRecyclerView == null)
            mRecyclerView = new WeakReference<>((RecyclerView) parent);
        return onCreateVH(parent, viewType);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onClick(View v) {
        RecyclerView recyclerView = mRecyclerView.get();
        if (recyclerView != null) {
            int position = recyclerView.getChildAdapterPosition(v);
            mOnItemClickListener.onItemClick(v, position);
        }
    }
}
