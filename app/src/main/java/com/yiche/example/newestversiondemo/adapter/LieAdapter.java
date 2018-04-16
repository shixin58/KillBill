package com.yiche.example.newestversiondemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiche.example.newestversiondemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by shixin on 2018/3/6.
 */
public class LieAdapter extends RecyclerView.Adapter<LieAdapter.ViewHolder> {
    private List<String> countryList = new ArrayList<>();

    public void setList(List<String> list) {
        countryList.clear();
        if(list!=null&&!list.isEmpty()) {
            countryList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lie, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(countryList.get(position));
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
