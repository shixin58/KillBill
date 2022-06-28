package com.bride.widget.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bride.widget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by shixin on 2019-08-05.
 */
public class MultiTypeAdapter extends BaseAdapter {

    public static final int TYPE_TEXT = 0;
    public static final int TYPE_GRID = 1;

    private final List<Type> mList = new ArrayList<>();

    public MultiTypeAdapter(List<Type> list) {
        mList.addAll(list);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Type getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextViewHolder textViewHolder = null;
        GridViewHolder gridViewHolder = null;
        int itemViewType = mList.get(position).getType();
        if (convertView == null) {
            if (itemViewType == TYPE_GRID) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_custom_grid, parent, false);
                gridViewHolder = new GridViewHolder();
                gridViewHolder.gridView = convertView.findViewById(R.id.gridView);
                convertView.setTag(gridViewHolder);
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
                textViewHolder = new TextViewHolder();
                textViewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
                convertView.setTag(textViewHolder);
            }
        } else {
            if (itemViewType == TYPE_GRID) {
                gridViewHolder = (GridViewHolder) convertView.getTag();
            } else {
                textViewHolder = (TextViewHolder) convertView.getTag();
            }
        }
        if (itemViewType == TYPE_GRID) {
            GridItem gridItem = (GridItem) mList.get(position);
            CustomGridViewAdapter adapter = (CustomGridViewAdapter) gridViewHolder.gridView.getAdapter();
            if (adapter == null) {
                adapter = new CustomGridViewAdapter(gridItem.titles);
            } else {// notifyDataSetChanged后GridView不刷新，重新setAdapter
                adapter.setList(gridItem.titles);
            }
            gridViewHolder.gridView.setAdapter(adapter);
        } else {
            TextItem textItem = (TextItem) mList.get(position);
            textViewHolder.tvTitle.setText(textItem.title);
        }
        return convertView;
    }

    public interface Type {
        int getType();
    }

    public static class TextItem implements Type {

        public String title;

        public TextItem(String title) {
            this.title = title;
        }

        @Override
        public int getType() {
            return TYPE_TEXT;
        }
    }

    public static class GridItem implements Type {

        public List<String> titles = new ArrayList<>();

        public GridItem(List<String> titles) {
            this.titles.addAll(titles);
        }

        @Override
        public int getType() {
            return TYPE_GRID;
        }
    }

    static class TextViewHolder {
        TextView tvTitle;
    }

    static class GridViewHolder {
        GridView gridView;
    }
}
