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

    private List<Type> mList = new ArrayList<>();

    public MultiTypeAdapter(List<Type> list) {
        mList.addAll(list);
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (mList.get(position).getType() == TYPE_GRID) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_custom_grid, parent, false);
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            }
        }
        if (mList.get(position).getType() == TYPE_GRID) {
            GridItem gridItem = (GridItem) mList.get(position);
            GridView gridView = convertView.findViewById(R.id.gridView);
            CustomGridViewAdapter adapter = (CustomGridViewAdapter) gridView.getAdapter();
            if (adapter == null) {
                adapter = new CustomGridViewAdapter(gridItem.titles);
            } else {
                // notifyDataSetChanged后GridView不刷新，重新setAdapter
                adapter.setList(gridItem.titles);
            }
            gridView.setAdapter(adapter);
        } else {
            TextItem textItem = (TextItem) mList.get(position);
            TextView tvTitle = convertView.findViewById(R.id.tv_title);
            tvTitle.setText(textItem.title);
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
}
