package com.bride.demon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bride.demon.R;
import com.bride.ui_lib.BaseRecyclerAdapter;

/**
 * <p>Created by shixin on 2019/4/14.
 */
public class RelativeLayoutAdapter extends BaseRecyclerAdapter<RelativeLayoutAdapter.CustomViewHolder, String> {

    @Override
    public void onBindVH(CustomViewHolder holder, int position) {
        holder.tvContent.setText(mList.get(position));
    }

    @Override
    public CustomViewHolder onCreateVH(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_relative_layout, parent, false));
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
