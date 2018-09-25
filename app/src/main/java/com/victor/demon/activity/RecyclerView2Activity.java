package com.victor.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.victor.demon.R;
import com.victor.demon.adapter.NestedAdapter;
import com.victor.demon.repository.RecyclerViewRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by shixin on 2018/9/26.
 */
public class RecyclerView2Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RecyclerView2Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view2);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        NestedAdapter nestedAdapter = new NestedAdapter();
        mRecyclerView.setAdapter(nestedAdapter);
        mRecyclerView.addOnItemTouchListener(new MyOnItemTouchListener());
        mRecyclerView.addOnScrollListener(new MyOnScrollListener());

        List<List<String>> lists = new ArrayList<>();
        lists.add(RecyclerViewRepository.Companion.getCountryList());
        lists.add(RecyclerViewRepository.Companion.getColorList());
        nestedAdapter.setList(lists);
    }

    private class MyOnItemTouchListener implements RecyclerView.OnItemTouchListener {

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    }

    private class MyOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    }
}
