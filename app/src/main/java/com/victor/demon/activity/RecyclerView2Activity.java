package com.victor.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;

import com.victor.demon.CellScrollHolder;
import com.victor.demon.R;
import com.victor.demon.adapter.NestedAdapter;
import com.victor.demon.repository.RecyclerViewRepository;
import com.victor.demon.widget.DispatchFrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by shixin on 2018/9/26.
 */
public class RecyclerView2Activity extends AppCompatActivity {

    private DispatchFrameLayout mDispatchFrameLayout;
    private RecyclerView mRecyclerView;
    private float oldX;
    private float oldY;

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
        mDispatchFrameLayout = findViewById(R.id.fl_dispatch);
        mRecyclerView = findViewById(R.id.recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        NestedAdapter nestedAdapter = new NestedAdapter();
        mRecyclerView.setAdapter(nestedAdapter);
        mRecyclerView.addOnItemTouchListener(new MyOnItemTouchListener());
        mRecyclerView.addOnScrollListener(new MyOnScrollListener());

        final CellScrollHolder cellScrollHolder = new CellScrollHolder();
        nestedAdapter.setCellScrollHolder(cellScrollHolder);
        List<List<String>> lists = new ArrayList<>();
        lists.add(RecyclerViewRepository.Companion.getCountryList());
        lists.add(RecyclerViewRepository.Companion.getColorList());
        nestedAdapter.setList(lists);

        mDispatchFrameLayout.setDispatchTouchEventListener(new DispatchFrameLayout.DispatchTouchEventListener() {
            @Override
            public void dispatchTouchEvent(MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = ev.getX();
                        oldY = ev.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        cellScrollHolder.notifyScroll((int) (oldX - ev.getX()), (int) (oldY - ev.getY()));
                        Log.i("dispatchTouchEvent", "ACTION_MOVE-"+(oldX - ev.getX())+" - "+(int) (oldY - ev.getY()));
                        oldX = ev.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        oldX = 0;
                        oldY = 0;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        oldX = 0;
                        oldY = 0;
                        break;
                }
            }
        });
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
