package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;

import com.bride.ui_lib.BaseActivity;
import com.bride.demon.CellScrollHolder;
import com.bride.demon.R;
import com.bride.demon.adapter.LieAdapter;
import com.bride.demon.adapter.NestedAdapter;
import com.bride.demon.repository.RecyclerViewRepository;
import com.bride.demon.widget.DispatchFrameLayout;
import com.bride.demon.widget.HeaderRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Created by shixin on 2018/9/26.
 */
public class NestedListActivity extends BaseActivity {
    private static final String TAG = NestedListActivity.class.getSimpleName();

    private HeaderRecyclerView mHeaderRecyclerView;
    private DispatchFrameLayout mDispatchFrameLayout;
    private RecyclerView mRecyclerView;
    private float oldX;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, NestedListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_list);
        initView();
    }

    private void initView() {
        mHeaderRecyclerView = findViewById(R.id.header_recycler_view);
        LinearLayoutManager headerLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mHeaderRecyclerView.setLayoutManager(headerLayoutManager);
        LieAdapter lieAdapter = new LieAdapter();
        mHeaderRecyclerView.setAdapter(lieAdapter);
        lieAdapter.setList(RecyclerViewRepository.Companion.getColorList());

        mDispatchFrameLayout = findViewById(R.id.fl_dispatch);
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        NestedAdapter nestedAdapter = new NestedAdapter();
        nestedAdapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header, mRecyclerView, false));
        nestedAdapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.footer, mRecyclerView, false));
        mRecyclerView.setAdapter(nestedAdapter);
        mRecyclerView.addOnItemTouchListener(new MyOnItemTouchListener());
        mRecyclerView.addOnScrollListener(new MyOnScrollListener());

        final CellScrollHolder cellScrollHolder = new CellScrollHolder();
        mHeaderRecyclerView.setScrollHandler(cellScrollHolder);
        nestedAdapter.setCellScrollHolder(cellScrollHolder);
        List<List<String>> lists = new ArrayList<>();
        String[] countries = getResources().getStringArray(R.array.countries);
        String[] colors = getResources().getStringArray(R.array.colors);
        for(int i = 0; i < 20; i++) {
            lists.add(Arrays.asList(countries[i%countries.length], countries[(i+1)%countries.length],
                    countries[(i+2)%countries.length], countries[(i+3)%countries.length]));

            List<String> colorList = new ArrayList<>();
            Collections.addAll(colorList, colors);
            lists.add(colorList);
        }
        nestedAdapter.setList(lists);

        mDispatchFrameLayout.setDispatchTouchEventListener(new DispatchFrameLayout.DispatchTouchEventListener() {
            @Override
            public void dispatchTouchEvent(MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = ev.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mHeaderRecyclerView.scrollBy((int) (oldX - ev.getX()), 0);
                        oldX = ev.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        oldX = 0;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        oldX = 0;
                        break;
                }
            }
        });
    }

    private class MyOnItemTouchListener implements RecyclerView.OnItemTouchListener {

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            Log.i(TAG, "onInterceptTouchEvent - "+motionEvent.getAction());
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            Log.i(TAG, "onTouchEvent - "+motionEvent.getAction());
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {
            Log.i(TAG, "onRequestDisallowInterceptTouchEvent - "+b);
        }
    }

    private class MyOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            Log.i(TAG, "onScrolled - "+dx+" - "+dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    break;
            }
            Log.i(TAG, "onScrollStateChanged - "+newState);
        }
    }
}
