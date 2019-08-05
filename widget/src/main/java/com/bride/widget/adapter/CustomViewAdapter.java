package com.bride.widget.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bride.widget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by shixin on 2019-07-29.
 */
public class CustomViewAdapter extends BaseAdapter {

    private List<String> mList = new ArrayList<>();

    public CustomViewAdapter(List<String> list) {
        mList.addAll(list);
    }

    public void setList(List<String> list) {
        Log.i("CustomViewAdapter", "setList");
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
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        }
        TextView tvTitle = (TextView) convertView;
        tvTitle.setText(mList.get(position));
        return convertView;
    }
}
