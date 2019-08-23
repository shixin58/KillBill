package com.bride.widget.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.bride.ui_lib.BaseActivity;
import com.bride.widget.R;
import com.bride.widget.adapter.CustomGridViewAdapter;
import com.bride.widget.adapter.GridViewAdapter;
import com.bride.widget.adapter.MultiTypeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Created by shixin on 2019-07-29.
 */
public class CustomViewActivity extends BaseActivity {

    private String[] weather;
    private String[] colors;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, CustomViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        GridView titleGv = findViewById(R.id.gridView);
        ListView listView = findViewById(R.id.listView);

        weather = getResources().getStringArray(R.array.weather);
        colors = getResources().getStringArray(R.array.color_grid);

        titleGv.setAdapter(new GridViewAdapter(Arrays.asList(colors)));

        View headerView = LayoutInflater.from(this).inflate(R.layout.view_custom_grid, listView, false);
        GridView headerGridView = headerView.findViewById(R.id.gridView);
        headerGridView.setAdapter(new CustomGridViewAdapter(Arrays.asList(colors)));
        listView.addHeaderView(headerView, null, false);

        View footerView = LayoutInflater.from(this).inflate(R.layout.item_custom_text, listView, false);
        listView.addFooterView(footerView, null, false);

        List<MultiTypeAdapter.Type> list = new ArrayList<>();
        String[] colorsCopy = new String[this.colors.length];
        List<String> tmpList = Arrays.asList(colorsCopy);
        System.arraycopy(colors, 0, colorsCopy, 0, colors.length);
        for(int i = 0; i<this.weather.length; i++) {
            list.add(new MultiTypeAdapter.TextItem(weather[i]));
            Collections.rotate(tmpList, 1);
            list.add(new MultiTypeAdapter.GridItem(tmpList));
        }
        listView.setAdapter(new MultiTypeAdapter(list));
    }
}
