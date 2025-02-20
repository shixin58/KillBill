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
import com.bride.widget.databinding.ActivityRefreshBinding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.Arrays;
import java.util.List;

/**
 * 展示RecyclerView列表搭配SmartRefreshLayout实现下拉刷新、上拉加载下一页
 * <p>Created by shixin on 2019-08-06.
 */
public class RefreshActivity extends BaseActivity {
    private ActivityRefreshBinding mBinding;

    private RecyclerView mRecyclerView;
    private RefreshAdapter mAdapter;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RefreshActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRefreshBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initView();
    }

    private void initView() {
        RefreshLayout refreshLayout = mBinding.refreshLayout;
//        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
//        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 下拉刷新逻辑
                List<String> list = Arrays.asList(getResources().getStringArray(R.array.colors));
                refreshLayout.finishRefresh();
                mAdapter.setList(list);
                refreshLayout.setEnableLoadMore(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 上拉加载下一页逻辑
                List<String> list = Arrays.asList(getResources().getStringArray(R.array.countries));
                refreshLayout.finishLoadMore();
                mAdapter.addMore(list);
                refreshLayout.setEnableLoadMore(false);
            }
        });

        mRecyclerView = mBinding.recyclerView;
        // 给RecyclerView设置垂直线性布局
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mAdapter = new RefreshAdapter();
        mRecyclerView.setAdapter(mAdapter);
        refreshLayout.autoRefresh();
    }
}
