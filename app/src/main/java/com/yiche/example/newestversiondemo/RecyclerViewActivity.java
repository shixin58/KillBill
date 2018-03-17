package com.yiche.example.newestversiondemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Created by shixin on 2018/3/6.
 */
public class RecyclerViewActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view_horizontal)
    RecyclerView recyclerViewHorizontal;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        LieAdapter lieAdapter = new LieAdapter();
        recyclerViewHorizontal.setAdapter(lieAdapter);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        DividerItemDecoration dividerItemDecorationHorizontal = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        dividerItemDecorationHorizontal.setDrawable(getResources().getDrawable(R.drawable.shape_vertical));
        recyclerViewHorizontal.addItemDecoration(dividerItemDecorationHorizontal);
        List<String> countryList = new ArrayList<>();
        countryList.add("America");
        countryList.add("Canada");
        countryList.add("Australia");
        countryList.add("Britain");
        lieAdapter.setList(countryList);

        StandAdapter standAdapter = new StandAdapter();
        recyclerView.setAdapter(standAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_horizontal));
        recyclerView.addItemDecoration(dividerItemDecoration);
        List<String> colorList = new ArrayList<>();
        colorList.add("Orange");
        colorList.add("Yellow");
        colorList.add("Brown");
        colorList.add("Black");
        colorList.add("Red");
        standAdapter.setFruitList(colorList);
    }
}