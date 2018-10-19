package com.bride.demon.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bride.baselib.widget.BaseRecyclerAdapter;
import com.bride.demon.CellScrollHolder;
import com.bride.demon.R;

import java.util.List;

/**
 * <p>Created by shixin on 2018/9/25.
 */
public class NestedAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder, List<String>> {
    private CellScrollHolder cellScrollHolder;

    public void setList(List<List<String>> lists) {
        this.mList.clear();
        if(lists!=null && !lists.isEmpty()) {
            this.mList.addAll(lists);
        }
        notifyDataSetChanged();
    }

    public void setCellScrollHolder(CellScrollHolder holder) {
        this.cellScrollHolder = holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position>=getHeaderViewCount() && position<getItemCount()-getFooterViewCount())
            return position%2;
        else
            return super.getItemViewType(position);
    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)<0) return;
        if(getItemViewType(position)==0) {
            AppleViewHolder viewHolder = (AppleViewHolder) holder;
            LieAdapter lieAdapter = new LieAdapter();
            lieAdapter.setList(mList.get(position-getHeaderViewCount()));
            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder.recyclerView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            viewHolder.recyclerView.setAdapter(lieAdapter);
        }else {
            BananaViewHolder viewHolder = (BananaViewHolder) holder;
            StandAdapter standAdapter = new StandAdapter();
            standAdapter.setFruitList(mList.get(position-getHeaderViewCount()));
            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder.recyclerView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            viewHolder.recyclerView.setAdapter(standAdapter);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        if(viewType<0)
            return new HeaderOrFooterViewHolder(getHeaderOrFooter(viewType));
        if(viewType == 0) {
            AppleViewHolder appleViewHolder = new AppleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_nested_apple, parent, false));
            cellScrollHolder.register(appleViewHolder.recyclerView);
            return appleViewHolder;
        }else {
            BananaViewHolder bananaViewHolder = new BananaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_nested_banana, parent, false));
            cellScrollHolder.register(bananaViewHolder.recyclerView);
            return bananaViewHolder;
        }
    }

    class AppleViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public AppleViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
        }
    }

    class BananaViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public BananaViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
        }
    }
}
