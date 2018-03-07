package com.yiche.example.newestversiondemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Created by shixin on 2018/3/6.
 */
public class StandAdapter extends RecyclerView.Adapter<StandAdapter.ViewHolder> {
    private List<String> fruitList = new ArrayList<>();

    public void setFruitList(List<String> fruitList) {
        this.fruitList.clear();
        if(fruitList!=null&&!fruitList.isEmpty()) {
            this.fruitList.addAll(fruitList);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_stand, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(fruitList.get(position));
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
