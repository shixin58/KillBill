package com.bride.widget.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bride.ui_lib.BaseRecyclerAdapter;
import com.bride.widget.R;

/**
 * <p>Created by shixin on 2019-08-06.
 */
public class RefreshAdapter extends BaseRecyclerAdapter<RefreshAdapter.CustomViewHolder, String> {

    @Override
    public void onBindVH(RefreshAdapter.CustomViewHolder holder, int position) {
        holder.tvTitle.setText(mList.get(position));
    }

    @Override
    public RefreshAdapter.CustomViewHolder onCreateVH(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false));
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
