package com.bride.demon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bride.demon.CellScrollHolder;
import com.bride.demon.R;
import com.bride.ui_lib.BaseRecyclerAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Created by shixin on 2018/9/25.
 */
public class NestedAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder, List<String>> {
    private CellScrollHolder cellScrollHolder;

    public void setCellScrollHolder(CellScrollHolder holder) {
        this.cellScrollHolder = holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position >= getHeaderViewCount() && position < (getItemCount() - getFooterViewCount()))
            return (position-getHeaderViewCount()) % 2;
        else
            return super.getItemViewType(position);
    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) < 0) return;
        if(getItemViewType(position) == 1) {
            AppleViewHolder viewHolder = (AppleViewHolder) holder;
            LieAdapter lieAdapter = new LieAdapter();
            lieAdapter.setList(mList.get(position - getHeaderViewCount()));
            viewHolder.recyclerView.setAdapter(lieAdapter);
        }else {
            BananaViewHolder viewHolder = (BananaViewHolder) holder;
            StandAdapter standAdapter = new StandAdapter();
            standAdapter.setFruitList(mList.get(position - getHeaderViewCount()));
            viewHolder.recyclerView.setAdapter(standAdapter);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        if(viewType < 0)
            return new HeaderOrFooterViewHolder(getHeaderOrFooter(viewType));
        if(viewType == 1) {
            AppleViewHolder appleViewHolder = new AppleViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_nested_apple, parent, false));
            cellScrollHolder.register(appleViewHolder.recyclerView);
            return appleViewHolder;
        }else {
            BananaViewHolder bananaViewHolder = new BananaViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_nested_banana, parent, false));
            return bananaViewHolder;
        }
    }

    class AppleViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public AppleViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
        }
    }

    class BananaViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public BananaViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
        }
    }
}
