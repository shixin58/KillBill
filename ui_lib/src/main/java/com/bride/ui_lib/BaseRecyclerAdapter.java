package com.bride.ui_lib;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>Created by shixin on 2018/4/19.
 */
public abstract class BaseRecyclerAdapter<V extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<V> implements View.OnClickListener {
    protected OnItemClickListener mOnItemClickListener;
    protected WeakReference<RecyclerView> mRecyclerView;
    protected List<T> mList = new ArrayList<>();

    private List<View> mHeaderViews = new LinkedList<>();
    private List<View> mFooterViews = new LinkedList<>();

    public void setList(List<T> list) {
        if (list != null && !list.isEmpty()) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addMore(List<T> list) {
        if (list != null && !list.isEmpty()) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }
    public void addHeaderView(View view) {
        mHeaderViews.add(view);
    }

    public int getHeaderViewCount() {
        return mHeaderViews.size();
    }

    public void addFooterView(View view) {
        mFooterViews.add(view);
    }

    public int getFooterViewCount() {
        return mFooterViews.size();
    }

    public View getHeaderOrFooter(int viewType) {
        if(viewType>=0) return null;
        if(mHeaderViews.size()>0 && -viewType<=mHeaderViews.size()) {
            return mHeaderViews.get(-viewType-1);
        }else {
            return mFooterViews.get(-viewType-mHeaderViews.size()-1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderViews.size()>0 && position<mHeaderViews.size()) {
            return -(position+1);
        }
        if(mFooterViews.size()>0 && getItemCount()-position<=mFooterViews.size()) {
            return -(position-mList.size()+1);
        }
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position) {
        if(mHeaderViews.size()>0 && position<mHeaderViews.size()) {
            return null;
        }
        if(mFooterViews.size()>0 && getItemCount()-position<=mFooterViews.size()) {
            return null;
        }
        return mList.get(position-mHeaderViews.size());
    }

    @Override
    public int getItemCount() {
        return mList.size()+mHeaderViews.size()+mFooterViews.size();
    }

    abstract public void onBindVH(V holder, int position);

    public abstract V onCreateVH(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(V holder, int position) {
        if(mOnItemClickListener!=null) {
            holder.itemView.setOnClickListener(this);
        }
        onBindVH(holder, position);
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
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

    public static class HeaderOrFooterViewHolder extends RecyclerView.ViewHolder {
        public HeaderOrFooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
