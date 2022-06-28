package com.bride.widget.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bride.widget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by shixin on 2019-08-23.
 */
public class GridViewAdapter extends BaseAdapter {

    private final List<String> mList = new ArrayList<>();

    public GridViewAdapter(List<String> list) {
        mList.addAll(list);
    }

    public void setList(List<String> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(mList.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView tvTitle;
    }
}
