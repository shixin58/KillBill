package com.bride.widget.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bride.ui_lib.BaseActivity;
import com.bride.widget.R;
import com.bride.widget.adapter.RefreshAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Created by shixin on 2019-08-06.
 */
public class RefreshActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    RefreshAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RefreshActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        initView();
    }

    private void initView() {
        RefreshLayout refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                List<String> list = Arrays.asList(getResources().getStringArray(R.array.colors));
                refreshLayout.finishRefresh();
                mAdapter.setList(list);
                refreshLayout.setEnableLoadMore(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                List<String> list = Arrays.asList(getResources().getStringArray(R.array.countries));
                refreshLayout.finishLoadMore();
                mAdapter.addMore(list);
                refreshLayout.setEnableLoadMore(false);
            }
        });
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mAdapter = new RefreshAdapter();
        mRecyclerView.setAdapter(mAdapter);
        refreshLayout.autoRefresh();
    }
}
